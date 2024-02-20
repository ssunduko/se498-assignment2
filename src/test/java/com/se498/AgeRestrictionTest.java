package com.se498;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.mockito.quality.Strictness;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.Mockito.*;

public class AgeRestrictionTest {


    @Test
    public void testAgeRestrictionException(){
        //TODO: Implement Throws Assertion test given invalid age
    }

    @TestFactory
    public Stream<DynamicTest> testDynamicInvalidAge() {
        //TODO: Implement Dynamic Assertions test given dynamic invalid inputs
        return IntStream.iterate(1, number -> number + 1).limit(17)
                .mapToObj(number -> dynamicTest("Checking" + number, () -> assertEquals(number, number)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "", "", ""})
    public void testValidAge() {
        //TODO: Implement Parametrized Assertions test given four valid inputs
    }

    @Test
    public void testUSDateConversion(){
        //TODO: Implement Spy test verifying US date format conversion
    }

    @Test
    public void testEUDateConversion(){
        //TODO: Implement Stub test verifying EU date format conversion
    }

    @Test
    public void testBaseConversion() {

        Participant dummyParticipant = new Participant();
        dummyParticipant.setLocale("EU");
        dummyParticipant.setDateOfBirth("20022007");

        ConversionStrategy strategy = null;
        try {
            strategy = ConversionStrategyFactory.getInstance().getStrategy(dummyParticipant.getLocale());
        } catch (AgeRestrictionException e) {
            throw new RuntimeException(e);
        }

        Command command = new ConversionCommand(strategy, dummyParticipant.getDateOfBirth());

        try {
            Date birthDate = (Date) command.execute();

            BusinessRuleService ruleService = new BusinessRuleService();

            Period yearsBetween = Period.between(birthDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate(), LocalDate.now());

            assertFalse(ruleService.applyBusinessRule(new AgeRestrictionBusinessRule(), yearsBetween.getYears()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
