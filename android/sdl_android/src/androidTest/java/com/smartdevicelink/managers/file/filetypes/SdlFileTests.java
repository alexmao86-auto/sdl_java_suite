/*
 * Copyright (c) 2019 Livio, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.smartdevicelink.managers.file.filetypes;

import android.support.test.runner.AndroidJUnit4;

import com.smartdevicelink.test.TestValues;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SdlFileTests {

    @Test
    public void testConstructorWithNoParams() {
        SdlFile sdlFile;

        // Case 1 (Setting data)
        sdlFile = new SdlFile();
        sdlFile.setFileData(TestValues.GENERAL_BYTE_ARRAY);
        assertEquals(sdlFile.getFileData(), TestValues.GENERAL_BYTE_ARRAY);
        sdlFile.setName(null);
        assertEquals(sdlFile.getName(), "e9800998ecf8427e");
        sdlFile.setName(TestValues.GENERAL_STRING);
        assertEquals(sdlFile.getName(), TestValues.GENERAL_STRING);
        sdlFile.setType(TestValues.GENERAL_FILETYPE);
        assertEquals(sdlFile.getType(), TestValues.GENERAL_FILETYPE);
        sdlFile.setPersistent(TestValues.GENERAL_BOOLEAN);
        assertEquals(sdlFile.isPersistent(), TestValues.GENERAL_BOOLEAN);

        // Case 2 (Setting resourceId)
        sdlFile = new SdlFile();
        sdlFile.setResourceId(TestValues.GENERAL_INTEGER);
        assertEquals((Integer) sdlFile.getResourceId(), TestValues.GENERAL_INTEGER);
        sdlFile.setName(null);
        assertEquals(sdlFile.getName(), "ec9ebc78777cf40d");
        sdlFile.setName(TestValues.GENERAL_STRING);
        assertEquals(sdlFile.getName(), TestValues.GENERAL_STRING);
        sdlFile.setType(TestValues.GENERAL_FILETYPE);
        assertEquals(sdlFile.getType(), TestValues.GENERAL_FILETYPE);
        sdlFile.setPersistent(TestValues.GENERAL_BOOLEAN);
        assertEquals(sdlFile.isPersistent(), TestValues.GENERAL_BOOLEAN);

        // Case 3 (Setting URI)
        sdlFile = new SdlFile();
        sdlFile.setUri(TestValues.GENERAL_URI);
        assertEquals(sdlFile.getUri(), TestValues.GENERAL_URI);
        sdlFile.setName(null);
        assertEquals(sdlFile.getName(), "d3467db131372140");
        sdlFile.setName(TestValues.GENERAL_STRING);
        assertEquals(sdlFile.getName(), TestValues.GENERAL_STRING);
        sdlFile.setType(TestValues.GENERAL_FILETYPE);
        assertEquals(sdlFile.getType(), TestValues.GENERAL_FILETYPE);
        sdlFile.setPersistent(TestValues.GENERAL_BOOLEAN);
        assertEquals(sdlFile.isPersistent(), TestValues.GENERAL_BOOLEAN);
    }

    @Test
    public void testConstructorWithResourceId() {
        // Case1 (Set the name manually)
        SdlFile sdlFile1 = new SdlFile(TestValues.GENERAL_STRING, TestValues.GENERAL_FILETYPE, TestValues.GENERAL_INTEGER, TestValues.GENERAL_BOOLEAN);
        assertEquals(sdlFile1.getName(), TestValues.GENERAL_STRING);
        assertEquals(sdlFile1.getType(), TestValues.GENERAL_FILETYPE);
        assertEquals((Integer) sdlFile1.getResourceId(), TestValues.GENERAL_INTEGER);
        assertEquals(sdlFile1.isPersistent(), TestValues.GENERAL_BOOLEAN);

        // Case2 (Let the library generate a name)
        SdlFile sdlFile2 = new SdlFile(null, TestValues.GENERAL_FILETYPE, TestValues.GENERAL_INTEGER, TestValues.GENERAL_BOOLEAN);
        SdlFile sdlFile3 = new SdlFile(null, TestValues.GENERAL_FILETYPE, TestValues.GENERAL_INTEGER, TestValues.GENERAL_BOOLEAN);
        assertEquals(sdlFile2.getName(), sdlFile3.getName());
        assertEquals(sdlFile2.getName(), "ec9ebc78777cf40d");
        assertEquals(sdlFile2.getType(), TestValues.GENERAL_FILETYPE);
        assertEquals((Integer) sdlFile2.getResourceId(), TestValues.GENERAL_INTEGER);
        assertEquals(sdlFile2.isPersistent(), TestValues.GENERAL_BOOLEAN);
    }

    @Test
    public void testConstructorWithData() {
        // Case1 (Set the name manually)
        SdlFile sdlFile1 = new SdlFile(TestValues.GENERAL_STRING, TestValues.GENERAL_FILETYPE, TestValues.GENERAL_BYTE_ARRAY, TestValues.GENERAL_BOOLEAN);
        assertEquals(sdlFile1.getName(), TestValues.GENERAL_STRING);
        assertEquals(sdlFile1.getType(), TestValues.GENERAL_FILETYPE);
        assertEquals(sdlFile1.getFileData(), TestValues.GENERAL_BYTE_ARRAY);
        assertEquals(sdlFile1.isPersistent(), TestValues.GENERAL_BOOLEAN);

        // Case2 (Let the library generate a name)
        SdlFile sdlFile2 = new SdlFile(null, TestValues.GENERAL_FILETYPE, TestValues.GENERAL_BYTE_ARRAY, TestValues.GENERAL_BOOLEAN);
        SdlFile sdlFile3 = new SdlFile(null, TestValues.GENERAL_FILETYPE, TestValues.GENERAL_BYTE_ARRAY, TestValues.GENERAL_BOOLEAN);
        assertEquals(sdlFile2.getName(), sdlFile3.getName());
        assertEquals(sdlFile2.getName(), "e9800998ecf8427e");
        assertEquals(sdlFile2.getType(), TestValues.GENERAL_FILETYPE);
        assertEquals(sdlFile2.getFileData(), TestValues.GENERAL_BYTE_ARRAY);
        assertEquals(sdlFile2.isPersistent(), TestValues.GENERAL_BOOLEAN);
    }

    @Test
    public void testConstructorWithUri() {
        // Case1 (Set the name manually)
        SdlFile sdlFile1 = new SdlFile(TestValues.GENERAL_STRING, TestValues.GENERAL_FILETYPE, TestValues.GENERAL_URI, TestValues.GENERAL_BOOLEAN);
        assertEquals(sdlFile1.getName(), TestValues.GENERAL_STRING);
        assertEquals(sdlFile1.getType(), TestValues.GENERAL_FILETYPE);
        assertEquals(sdlFile1.getUri(), TestValues.GENERAL_URI);
        assertEquals(sdlFile1.isPersistent(), TestValues.GENERAL_BOOLEAN);

        // Case2 (Let the library generate a name)
        SdlFile sdlFile2 = new SdlFile(null, TestValues.GENERAL_FILETYPE, TestValues.GENERAL_URI, TestValues.GENERAL_BOOLEAN);
        SdlFile sdlFile3 = new SdlFile(null, TestValues.GENERAL_FILETYPE, TestValues.GENERAL_URI, TestValues.GENERAL_BOOLEAN);
        assertEquals(sdlFile2.getName(), sdlFile3.getName());
        assertEquals(sdlFile2.getName(), "d3467db131372140");
        assertEquals(sdlFile2.getType(), TestValues.GENERAL_FILETYPE);
        assertEquals(sdlFile2.getUri(), TestValues.GENERAL_URI);
        assertEquals(sdlFile2.isPersistent(), TestValues.GENERAL_BOOLEAN);
    }
}
