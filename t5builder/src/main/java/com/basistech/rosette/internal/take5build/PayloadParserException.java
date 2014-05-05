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


package com.basistech.rosette.internal.take5build;

/**
 * Parsing exception on a payload.
 */
public class PayloadParserException extends Exception {
    private final int column;

    public PayloadParserException(String message, int column) {
        super(message);
        this.column = column;
    }

    public PayloadParserException(String message, Throwable cause, int column) {
        super(message, cause);
        this.column = column;
    }

    public int getColumn() {
        return column;
    }
}
