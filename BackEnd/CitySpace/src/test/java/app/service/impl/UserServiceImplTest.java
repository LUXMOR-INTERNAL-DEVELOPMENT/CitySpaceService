package app.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import app.dao.UserDao;
import app.dto.StoreCategoryResponse;
import app.entity.Event;
import app.entity.StoreCategory;
import app.exception.BadRequestException;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getNearbyStoreCategories_shouldReturnCategories() throws BadRequestException {

        // Arrange
        Event event = new Event();
        event.setCategoryId("CAT001");
        event.setLatitude(13.0827);
        event.setLongitude(80.2707);

        StoreCategory category = new StoreCategory();
        category.setCategoryId("CAT001");
        category.setCategoryName("Home & Craft");

        when(userDao.getActiveStoreEvents())
                .thenReturn(List.of(event));

        when(userDao.getCategoryById("CAT001"))
                .thenReturn(category);

        // Act
        List<StoreCategoryResponse> result =
                userService.getNearbyStoreCategories(
                        13.0827,
                        80.2707,
                        10
                );

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("CAT001", result.get(0).getCategoryId());
        assertEquals("Home & Craft", result.get(0).getCategoryName());

        verify(userDao).getActiveStoreEvents();
        verify(userDao).getCategoryById("CAT001");
    }
    
    @Test
    void getNearbyStoreCategories_shouldThrowExceptionForInvalidLatitude() {

        // Act & Assert
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> userService.getNearbyStoreCategories(
                        100.0,
                        80.2707,
                        10
                )
        );

        assertEquals(
                "Latitude must be between -90 and 90",
                exception.getMessage()
        );

        verifyNoInteractions(userDao);
    }
    @Test
    void getNearbyStoreCategories_shouldThrowExceptionForInvalidLongitude() {

        // Act & Assert
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> userService.getNearbyStoreCategories(
                        13.0827,
                        200.0,
                        10
                )
        );

        assertEquals(
                "Longitude must be between -180 and 180",
                exception.getMessage()
        );

        verifyNoInteractions(userDao);
    }
    @Test
    void getNearbyStoreCategories_shouldThrowExceptionForInvalidRadius() {

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> userService.getNearbyStoreCategories(
                        13.0827,
                        80.2707,
                        0
                )
        );

        assertEquals(
                "Radius must be greater than 0",
                exception.getMessage()
        );

        verifyNoInteractions(userDao);
    }
    @Test
    void getNearbyStoreCategories_shouldIgnoreEventsWithNullCoordinates() {

        // Event with valid coordinates
        Event validEvent = new Event();
        validEvent.setCategoryId("CAT001");
        validEvent.setLatitude(13.0827);
        validEvent.setLongitude(80.2707);

        // Event with null latitude
        Event nullLatitudeEvent = new Event();
        nullLatitudeEvent.setCategoryId("CAT002");
        nullLatitudeEvent.setLatitude(null);
        nullLatitudeEvent.setLongitude(80.2707);

        // Event with null longitude
        Event nullLongitudeEvent = new Event();
        nullLongitudeEvent.setCategoryId("CAT003");
        nullLongitudeEvent.setLatitude(13.0827);
        nullLongitudeEvent.setLongitude(null);

        when(userDao.getActiveStoreEvents())
                .thenReturn(List.of(
                        validEvent,
                        nullLatitudeEvent,
                        nullLongitudeEvent
                ));

        StoreCategory category = new StoreCategory();
        category.setCategoryId("CAT001");
        category.setCategoryName("Home & Craft");

        when(userDao.getCategoryById("CAT001"))
                .thenReturn(category);

        // Act
        List<StoreCategoryResponse> result =
                userService.getNearbyStoreCategories(
                        13.0827,
                        80.2707,
                        10
                );

        // Assert
        assertEquals(1, result.size());
        assertEquals("CAT001", result.get(0).getCategoryId());
        assertEquals("Home & Craft", result.get(0).getCategoryName());

        verify(userDao).getCategoryById("CAT001");
        verify(userDao, never()).getCategoryById("CAT002");
        verify(userDao, never()).getCategoryById("CAT003");
    }
}