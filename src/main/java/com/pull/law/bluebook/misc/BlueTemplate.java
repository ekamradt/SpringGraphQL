package com.pull.law.bluebook.misc;

import java.util.HashMap;

public class BlueTemplate {

    // -------------------------------------------------------------------------
    // Blue Definition:
    // -------------------------------------------------------------------------
    // (c) State bills and resolutions.
    // When citing state bills and resolutions,
    //  *) include the name of the legislative body, abbreviated according to tables T6, T9, and T10,
    //  *) the number of the bill or resolution,
    //  *) the number of the legislative body (or, if not numbered, the year of the body),
    //  *) and the number or designation of the legislative session.
    //  *) Parenthetically indicate the name of the state, abbreviated according to table T10,
    //          and the year of enactment (for an enacted bill or resolution)
    //          or the year of publication (for an unenacted bill or resolution).
    // -------------------------------------------------------------------------
    // Baby Definition:
    // -------------------------------------------------------------------------
    // State bills and resolutions
    //  Cite as follows:
    //      *) <number of bill or resolution>,
    //      *) <number, or year if unnumbered, of the legislative body>,
    //      *) <number or designation of the legislative session>
    //      *) (<name of state, abbreviated as in Table T10, and year of enactment or publication, if unenacted>.)
    //
    // Baby Examples:
    //      L.D. 3, 127th Leg., Reg. Sess. (Me. 2015).
    // Blue Examples:
    //      H.D. 636, 1999 Leg., 413th Sess. (Md. 1999).
    //      H.R. 189, 145th Gen. Assemb., Reg. Sess. (Ga. 1999).
    // -------------------------------------------------------------------------

    // law_document_elemet --> document_version
    // law_document_catalog <-- document_version
    // law_document_catalog --> law_document_collection

    // document_version.published (YYYY)
    // law_document_catalog.document_id_prefix
    //  check for:
    //      law_document_collection
    //        SESSION-# == SESSION_NUMBER
    //          .Name ... US / State / Georgia / GEORGIA BILS  115th Congress(House of Reps)
    //          STATE?
    //          BILL- BILL_NUMBER

    public static final String US_STATE_HOUSE_BILL_FORMAT =
            "H.R. <BILL_NUMBER!>, <LEGISLATIVE_SESSION_NUMBER!> <LEGISLATIVE_SESSION!> <(STATE YYYY)>";


    public static final HashMap<String, String> map = new HashMap<>();

    static {


        map.put("BILL_NUMBER", "");

        

    }
}
