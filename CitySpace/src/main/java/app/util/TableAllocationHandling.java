package app.util;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dao.UserDao;
import app.dto.AvailableTable;
import app.dto.BookingTableAllocation;
import app.entity.Restaurant;
import app.exception.TableNotAvailableException;
import jakarta.transaction.Transactional;

@Component
public class TableAllocationHandling {
	@Autowired
    private UserDao userDao;

    @Transactional
    public void allocateTables(
            String vendorId,
            String bookingId,
            String bookingDate,
            String slotId,
            int numberOfGuests) {

        // Lock tables for concurrency
        List<Restaurant> tables =
                userDao.getTablesForUpdate(vendorId);

        if (tables == null || tables.isEmpty()) {
            throw new TableNotAvailableException(
                    "No tables are available for this vendor");
        }

        // Get tables available for date + slot
        List<AvailableTable> availableTables =
                userDao.getAvailableTables(
                        vendorId,
                        bookingDate,
                        slotId);

        if (availableTables == null ||
                availableTables.isEmpty()) {

            throw new TableNotAvailableException(
                    "No tables are available for the selected "
                    + "date and time slot");
        }

        // Check total capacity
        int totalCapacity = availableTables.stream()
                .mapToInt(AvailableTable::getCapacity)
                .sum();

        if (totalCapacity < numberOfGuests) {

            throw new TableNotAvailableException(
                    "Not enough table capacity available "
                    + "for the selected date and time slot");
        }

        // First try one table
        AvailableTable singleTable = availableTables.stream()
                .filter(table ->
                        table.getCapacity() >= numberOfGuests)
                .findFirst()
                .orElse(null);

        if (singleTable != null) {

            saveAllocation(
                    bookingId,
                    singleTable.getTableId(),
                    slotId,
                    bookingDate,
                    numberOfGuests);

            return;
        }

        // Multiple table allocation
        int remainingGuests = numberOfGuests;

        for (AvailableTable table : availableTables) {

            if (remainingGuests <= 0) {
                break;
            }

            int allocatedGuests =
                    Math.min(
                            remainingGuests,
                            table.getCapacity());

            saveAllocation(
                    bookingId,
                    table.getTableId(),
                    slotId,
                    bookingDate,
                    allocatedGuests);

            remainingGuests -= allocatedGuests;
        }

        if (remainingGuests > 0) {

            throw new TableNotAvailableException(
                    "Unable to allocate tables for "
                    + "all requested guests");
        }
    }

    private void saveAllocation(
            String bookingId,
            String tableId,
            String slotId,
            String bookingDate,
            int numberOfGuests) {

        BookingTableAllocation allocation =
                new BookingTableAllocation();

        allocation.setBookingTableAllocationId(
                "BT-" + UUID.randomUUID());

        allocation.setBookingId(
                bookingId);

        allocation.setTableId(
                tableId);

        allocation.setSlotId(
                slotId);

        allocation.setBookingDate(
                bookingDate);

        allocation.setNumberOfGuests(
                numberOfGuests);

        allocation.setBookingStatus(
                "RESERVED");

        userDao.saveTableBooking(allocation);
    }
}

