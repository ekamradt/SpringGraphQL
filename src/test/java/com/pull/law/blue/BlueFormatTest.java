package com.pull.law.blue;


import com.pull.law.bluebook.misc.BlueConverter;
import com.pull.law.bluebook.misc.BluePieces;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class BlueFormatTest {

    public static final String PARAM_SESSION = "SESSION";

    @InjectMocks
    private BlueConverter blueConverter;

    @Test
    public void requiredParamMissingTest() {
        try {
            final BluePieces bluePieces = new BluePieces();
            final String formatString = BlueTestHelper.wrapRequiredParamNameForTemplate(PARAM_SESSION);
            final String result = blueConverter.processFormat(formatString, bluePieces);
            fail("BlueConvert was suppose to throw a required parameter exception.");
        } catch (Exception e) {
            ; //
        }
    }

    @Test
    public void requiredParamFounTest() {
        try {
            final BluePieces bluePieces = new BluePieces();
            bluePieces.setSession("X");
            final String formatString = BlueTestHelper.wrapRequiredParamNameForTemplate(PARAM_SESSION);
            final String result = blueConverter.processFormat(formatString, bluePieces);
        } catch (Exception e) {
            fail("BlueConvert was suppose to NOT throw a required parameter exception.");
        }
    }
}
