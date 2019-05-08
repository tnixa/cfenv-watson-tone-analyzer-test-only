package org.terrence.testapp.rest;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.ibm.watson.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.tone_analyzer.v3.model.ToneOptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {

  @Autowired
  protected ToneAnalyzer toneAnalyzer;

  // Test the Insights by analyzing a test json file

  @RequestMapping(value = "/test", produces = "text/plain")
  public String runTest() {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    try {
      pw.println("Beginning test...");

      String testText = "Team, I know that times are tough! Product "
          + "sales have been disappointing for the past three " + "quarters. We have a competitive product, but we "
          + "need to do a better job of selling it!";

      ToneOptions toneOptions = new ToneOptions.Builder().text(testText).build();

      ToneAnalysis toneAnalysis = toneAnalyzer.tone(toneOptions).execute().getResult();
      System.out.println(toneAnalysis);

      // check to see if query exists in the results
      String expectedKeyword = "Analytical";
      if (toneAnalysis.toString().toLowerCase().contains(expectedKeyword.toLowerCase())) {
        pw.println("PASS: Tone Analyzer results contain expected keyword: " + expectedKeyword);
      } else {
        pw.println("FAIL: Tone Analyzer results do not contain expected keyword: " + expectedKeyword);
      }

    } catch (Exception e) {
      pw.println("FAIL: Unexpected error during test.");
      e.printStackTrace();
    }
    pw.flush();
    return sw.toString();
  }
}