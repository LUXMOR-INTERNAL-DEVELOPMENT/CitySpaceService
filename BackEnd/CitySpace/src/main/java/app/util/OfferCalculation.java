package app.util;

// Used for accurate money calculations
import java.math.BigDecimal;

// Used for rounding decimal values
import java.math.RoundingMode;

// Represents MONDAY, TUESDAY, ..., SUNDAY
import java.time.DayOfWeek;

// Represents date and time together
import java.time.LocalDateTime;


// Utility class for offer and coupon calculations
public class OfferCalculation {

    // Private constructor prevents creating an object
    // of this utility class.
    private OfferCalculation() {

    }


    // COMMAND:
    // Check whether the given date is Saturday or Sunday.
    //
    // RETURN:
    // true  -> Saturday or Sunday
    // false -> Monday to Friday
    public static boolean isWeekend(LocalDateTime dateTime) {

        // COMMAND:
        // Get the day from the given date and time.
        DayOfWeek day = dateTime.getDayOfWeek();

        // COMMAND:
        // Return true if the day is Saturday OR Sunday.
        return day == DayOfWeek.SATURDAY
                || day == DayOfWeek.SUNDAY;
    }


    // COMMAND:
    // Check whether the given date is Monday to Friday.
    //
    // RETURN:
    // true  -> Monday to Friday
    // false -> Saturday or Sunday
    public static boolean isWeekday(LocalDateTime dateTime) {

        // COMMAND:
        // Get the day from the given date and time.
        DayOfWeek day = dateTime.getDayOfWeek();

        // COMMAND:
        // Return true when the day is NOT Saturday
        // and NOT Sunday.
        return day != DayOfWeek.SATURDAY
                && day != DayOfWeek.SUNDAY;
    }


    // COMMAND:
    // Calculate the discount amount based on:
    //
    // 1. Booking amount
    // 2. Discount value
    // 3. Discount type
    // 4. Offer type
    // 5. Maximum discount
    public static BigDecimal calculateDiscount(

            BigDecimal amount,

            BigDecimal discountValue,

            String discountType,

            String offerType,

            BigDecimal maximumDiscount) {


        // COMMAND:
        // Initially set the discount to zero.
        BigDecimal discount = BigDecimal.ZERO;


        // COMMAND:
        // Check whether the offer is B2G1.
        if ("B2G1".equalsIgnoreCase(offerType)) {


            // COMMAND:
            // Calculate 33.33% of the booking amount.
            //
            // Example:
            // amount = 2000
            //
            // 2000 × 33.33 / 100
            // = 666.60
            discount = amount

                    .multiply(new BigDecimal("33.33"))

                    .divide(
                            BigDecimal.valueOf(100),
                            2,
                            RoundingMode.HALF_UP
                    );
        }


        // COMMAND:
        // If the offer is not B2G1,
        // check whether the discount type is PERCENTAGE.
        else if ("PERCENTAGE".equalsIgnoreCase(discountType)) {


            // COMMAND:
            // Calculate percentage discount.
            //
            // Example:
            // amount = 2000
            // discountValue = 20
            //
            // 2000 × 20 / 100
            // = 400
            discount = amount

                    .multiply(discountValue)

                    .divide(
                            BigDecimal.valueOf(100),
                            2,
                            RoundingMode.HALF_UP
                    );
        }


        // COMMAND:
        // If the discount type is FLAT,
        // use the discount value directly.
        else if ("FLAT".equalsIgnoreCase(discountType)) {


            // COMMAND:
            // Example:
            // discountValue = 300
            //
            // discount = 300
            discount = discountValue;
        }


        // COMMAND:
        // Check whether a maximum discount has been configured.
        //
        // maximumDiscount != null
        // means a maximum discount exists.
        //
        // discount.compareTo(maximumDiscount) > 0
        // means calculated discount is greater than
        // the allowed maximum.
        if (maximumDiscount != null

                && discount.compareTo(maximumDiscount) > 0) {


            // COMMAND:
            // If calculated discount is greater than
            // maximum discount, use the maximum discount.
            discount = maximumDiscount;
        }


        // COMMAND:
        // Make sure the discount does not exceed
        // the original booking amount.
        //
        // Example:
        // amount = 500
        // discount = 700
        //
        // Discount will become 500.
        if (discount.compareTo(amount) > 0) {

            discount = amount;
        }


        // COMMAND:
        // Return the final calculated discount.
        return discount;
    }


    // COMMAND:
    // Calculate the final amount after discount.
    //
    // Formula:
    //
    // Final Amount = Original Amount - Discount
    public static BigDecimal calculateFinalAmount(

            BigDecimal amount,

            BigDecimal discount) {


        // COMMAND:
        // Subtract discount from original amount.
        //
        // Example:
        // amount = 2000
        // discount = 400
        //
        // final amount = 1600
        //
        // setScale(2) keeps two decimal places.
        return amount.subtract(discount)

                .setScale(
                        2,
                        RoundingMode.HALF_UP
                );
    }
}