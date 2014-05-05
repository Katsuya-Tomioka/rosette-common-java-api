/******************************************************************************
 ** This data and information is proprietary to, and a valuable trade secret
 ** of, Basis Technology Corp.  It is given in confidence by Basis Technology
 ** and may only be used as permitted under the license agreement under which
 ** it has been distributed, and in no other way.
 **
 ** Copyright (c) 2014 Basis Technology Corporation All rights reserved.
 **
 ** The technical data and information provided herein are provided with
 ** `limited rights', and the computer software provided herein is provided
 ** with `restricted rights' as those terms are defined in DAR and ASPR
 ** 7-104.9(a).
 ******************************************************************************/

package com.basistech.t5build;

import com.basistech.rosette.internal.take5.Take5Match;
import com.basistech.rosette.internal.take5.Take5Dictionary;
import com.basistech.rosette.internal.take5build.Engine;
import com.basistech.rosette.internal.take5build.KeyFormat;
import com.basistech.rosette.internal.take5build.Take5Format;
import com.google.common.io.Files;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

/**
 * Tests that actually build a T5
 */
public class EndToEndTest extends Assert {

    @Test
    public void perfhashKeysOnly() throws Exception {
        File t5File = File.createTempFile("t5btest.", ".bin");
        t5File.deleteOnExit();
        File inputFile = new File("src/test/data/hex-keys.txt");
        Take5Build cmd = new Take5Build();
        cmd.noPayloads = true;
        cmd.engine = Engine.PERFHASH;
        cmd.outputFile = t5File;
        cmd.keyFormat = KeyFormat.HASH_STRING;
        cmd.commandInputFile = inputFile;
        cmd.checkOptionConsistency();
        cmd.build();

        ByteBuffer resultT5 = Files.map(t5File);
        Take5Dictionary dict = new Take5Dictionary(resultT5, resultT5.capacity());

        assertEquals(Take5Format.ENGINE_PERFHASH, dictSpyInt(dict, "fsaEngine"));

        Take5Match match = new Take5Match();
        assertTrue(dict.matchExact("bedded".toCharArray(), 0, "bedded".length(), match));
    }

    private int dictSpyInt(Take5Dictionary dict, String fieldName) {
        try {
            Field field = Take5Dictionary.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.getInt(dict);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
