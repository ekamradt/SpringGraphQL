package com.pull.law.bluebooksearch.service;

import com.pull.law.bluebooksearch.misc.BlueSearchRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlueSearchRecordService {

    final List<BlueSearchRecord> blueSearchRecords = new ArrayList<>();

    public List<BlueSearchRecord> buildCustomRecordsToSearch() {
        blueSearchRecords.clear();
        final String masterCustomTitle = "Custom Master Abbreviations";
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setName("Cal. Code Reg.")
                        .setValue("CCR")
                        .setNote("Semi-official")
                        .setUrlAddress("https://oal.ca.gov/publications/ccr/")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setName("Government")
                        .setValue("GOV")
                        .setNote("Offical abbreviation is \"Gov't\"")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setName("Article")
                        .setValue("ARTICLE")
                        .setNote("Offical abbreviation is \"Art. or Arts.\"")
        );
        // ILCS as the abbreviation for all forms of the Illinois Compiled Statutes
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Illinois Compiled Statutes")
                        .setName("Illinois Compiled Statutes")
                        .setValue("ILCS")
                        .setDates(null)
                        .setNote("Semi-offical abbreviation for 'Illinois Compiled Statutes'")
                        .setUrlAddress("https://www.ilga.gov/legislation/ilcs/ilcs.asp")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Administrative Code")
                        .setName("Administrative Code")
                        .setValue("ADC")
                        .setNote("Not the offical abbreviation for 'Administrative Code'")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("United States Code")
                        .setName("United States Code")
                        .setValue("U.S.C.")
                        .setUrlAddress("https://uscode.house.gov/")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Financial Institution Letters")
                        .setName("Financial Institution Letters")
                        .setValue("FIL.")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Code of Federal Regulations")
                        .setName("Code of Federal Regulations")
                        .setValue("C.F.R.")
                        .setUrlAddress("https://www.fdic.gov/news/financial-institution-letters/index.html")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Office of the Comptroller of the Currency")
                        .setName("Banking Circular")
                        .setValue("OCC Banking Circular")
                        .setUrlAddress("https://www.occ.gov/news-events/newsroom/news-issuances-by-year/bulletins/banking-circulars.html")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Office of the Comptroller of the Currency")
                        .setName("Bulletins")
                        .setValue("OCC Bulletin")
                        .setUrlAddress("https://www.occ.gov/topics/charters-and-licensing/weekly-bulletin/index-weekly-bulletin.html")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Office of the Comptroller of the Currency")
                        .setName("OCC Banking Bulletin")
                        .setValue("OCC Banking Bulletin")
                        .setUrlAddress("https://www.occ.gov/topics/charters-and-licensing/weekly-bulletin/index-weekly-bulletin.html")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Office of the Comptroller of the Currency")
                        .setName("News Release")
                        .setValue("OCC NR")
                        .setUrlAddress("https://www.occ.gov/news-events/newsroom/news-issuances-by-year/news-releases/index-news-releases.html")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Office of the Comptroller of the Currency")
                        .setSubsubtitle("Advisory Letter")
                        .setName("Advisory Letter")
                        .setValue("OCC AL")
                        .setUrlAddress("https://www.occ.gov/news-events/newsroom/news-issuances-by-year/advisory-letters/index-advisory-letters.html")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Board of Governers of the Federal Reserve System")
                        .setSubsubtitle(null)
                        .setName("Supervision and Regulation Letters")
                        .setValue("SR")
                        .setUrlAddress("https://www.federalreserve.gov/supervisionreg/srletters/srletters.htm")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Board of Governers of the Federal Reserve System")
                        .setSubsubtitle(null)
                        .setName("Basel Coordination Committee Bulletins")
                        .setValue("BCC Bulletin")
                        .setUrlAddress("https://www.federalreserve.gov/supervisionreg/basel/basel-coordination-committee-bulletins.htm")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Federal Rules of Bankruptcy Procedure")
                        .setName("Federal Rules of Bankruptcy Procedure")
                        .setValue("FRBP Rule")
                        .setUrlAddress("https://www.uscourts.gov/rules-policies/current-rules-practice-procedure/federal-rules-bankruptcy-procedure")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Federal Rules of Bankruptcy Procedure")
                        .setName("Federal Rules of Bankruptcy Procedure")
                        .setValue("FRBP")
                        .setUrlAddress("https://www.uscourts.gov/rules-policies/current-rules-practice-procedure/federal-rules-bankruptcy-procedure")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("www.finra.org")
                        .setName("www.finra.org")
                        .setValue("FINRA Rule")
                        .setNote("FINRA is a government-authorized not-for-profit organization that oversees U.S. broker-dealers.")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("www.finra.org")
                        .setName("www.finra.org")
                        .setValue("NYSE Rule")
                        .setNote("FINRA is a government-authorized not-for-profit organization that oversees U.S. broker-dealers.")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Federal Deposit Insurance Corporation")
                        .setName("FDIC")
                        .setValue("FDIC")
                        .setUrlAddress("https://www.fdic.gov/")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("National Futures Association")
                        .setName("NFA Rule")
                        .setValue("NFA Rule")
                        .setNote("NFA is the industrywide, self-regulatory organization for the U.S. derivatives industry, providing innovative and effective regulatory programs.")
                        .setUrlAddress("https://www.nfa.futures.org/rulebook/index.aspx")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("National Futures Association")
                        .setSubsubtitle("https://www.nfa.futures.org/")
                        .setName("NFA Bylaw")
                        .setValue("NFA Bylaw")
                        .setNote("NFA is the industrywide, self-regulatory organization for the U.S. derivatives industry, providing innovative and effective regulatory programs.")
                        .setUrlAddress("https://www.nfa.futures.org/rulebook/index.aspx")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("National Futures Association")
                        .setSubsubtitle("https://www.nfa.futures.org/")
                        .setName("NFA Interpretive Notice")
                        .setValue("NFA Interpretive Notice")
                        .setNote("NFA is the industrywide, self-regulatory organization for the U.S. derivatives industry, providing innovative and effective regulatory programs.")
                        .setUrlAddress("https://www.nfa.futures.org/rulebook/index.aspx")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("National Futures Association")
                        .setSubsubtitle("https://www.nfa.futures.org/")
                        .setName("NFA Financial Requirements")
                        .setValue("NFA Financial Requirements")
                        .setNote("NFA is the industrywide, self-regulatory organization for the U.S. derivatives industry, providing innovative and effective regulatory programs.")
                        .setUrlAddress("https://www.nfa.futures.org/rulebook/index.aspx")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("CME Group")
                        .setSubsubtitle("https://www.cmegroup.com")
                        .setName("CME Rule")
                        .setValue("CME Rule")
                        .setNote("The world’s leading and most diverse derivatives marketplace.")
                        .setUrlAddress("https://www.cmegroup.com/rulebook/CME/")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("CME Group")
                        .setSubsubtitle("https://www.cmegroup.com")
                        .setName("CME Rulebook")
                        .setValue("CME Rulebook")
                        .setNote("The world’s leading and most diverse derivatives marketplace.")
                        .setUrlAddress("https://www.cmegroup.com/rulebook/CME/")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Municipal Securities Rulemaking Board")
                        .setName("MSRB Rule")
                        .setValue("MSRB Rule")
                        .setNote("Our Mission: To protect investors, municipal entities and the public interest by promoting a fair and efficient municipal market, regulating firms that engage in municipal securities and advisory activities, and promoting market transparency.")
                        .setUrlAddress("http://www.msrb.org/")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Financial Industry Regulatory Authority")
                        .setName("FINRA Notice")
                        .setValue("FINRA Notice")
                        .setNote("FINRA is authorized by Congress to protect America’s investors by making sure the broker-dealer industry operates fairly and honestly.")
                        .setUrlAddress("https://www.finra.org/rules-guidance/notices/")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Consumer Financial Protection Bureau")
                        .setName("CFPB Bulletin")
                        .setValue("CFPB Bulletin")
                        .setNote("The CFPB implements and enforces federal consumer financial laws to ensure that all consumers have access to markets for consumer financial products and services that are fair, transparent, and competitive.")
                        .setUrlAddress("https://www.consumerfinance.gov")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Consumer Financial Protection Bureau")
                        .setName("CFPB")
                        .setValue("CFPB")
                        .setNote("The CFPB implements and enforces federal consumer financial laws to ensure that all consumers have access to markets for consumer financial products and services that are fair, transparent, and competitive.")
                        .setUrlAddress("https://www.consumerfinance.gov")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Uniform Law Commission")
                        .setName("UETA Sec")
                        .setValue("UETA Sec")
                        .setNote("")
                        .setUrlAddress("https://www.uniformlaws.org/home")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Employee Retirement Income Security Act ")
                        .setName("ERISA")
                        .setValue("ERISA")
                        .setNote("")
                        .setUrlAddress("https://www.uniformlaws.org/home")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Prohibited Transaction Exemption")
                        .setName("PTE")
                        .setValue("PTE")
                        .setNote("")
                        .setUrlAddress("https://www.govinfo.gov/")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Code of Federal Regulations")
                        .setName("CFR")
                        .setValue("CFR")
                        .setNote("")
                        .setUrlAddress("https://www.govinfo.gov/")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Commodity Futures Trading Commission")
                        .setName("CFTC")
                        .setValue("CFTC")
                        .setNote("")
                        .setUrlAddress("https://www.cftc.gov")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Servicemembers Civil Relief Act ")
                        .setName("Corp SCRA Policy")
                        .setValue("Corp SCRA Policy")
                        .setNote("Official destination (we hope) 50 U.S.C. 3952 ")
                        .setUrlAddress("https://www.cftc.gov")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Servicemembers Civil Relief Act ")
                        .setName("Corporate SCRA Policy")
                        .setValue("Corporate SCRA Policy")
                        .setNote("Official destination (we hope) 50 U.S.C. §§ 3901-4043.")
                        .setUrlAddress("https://www.cftc.gov")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("U.S. Securities And Exchange commission")
                        .setName("SEC Risk Alert")
                        .setValue("SEC Risk Alert")
                        .setUrlAddress("https://www.sec.gov")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Fair Credit Reporting Act")
                        .setName("FCRA")
                        .setValue("FCRA")
                        .setUrlAddress("https://www.ftc.gov/enforcement/statutes/fair-credit-reporting-act")
        );

        // Bogus State abbreviations.
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Illinois")
                        .setSubsubtitle("Illinois")
                        .setName("Illinois")
                        .setValue("IL")
                        .setNote("Official abbreviation is 'Ill.'")
                        .setUrlAddress("https://www.ilga.gov/legislation/ilcs/ilcs.asp")
        );
        // 627
        return blueSearchRecords;
    }
}
