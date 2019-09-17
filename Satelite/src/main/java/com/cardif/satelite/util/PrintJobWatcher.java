package com.cardif.satelite.util;

import javax.print.DocPrintJob;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;

public class PrintJobWatcher {
  boolean done = false;

  public PrintJobWatcher(DocPrintJob job) {
    // Add a listener to the print job
    job.addPrintJobListener(new PrintJobAdapter() {
      @Override
      public void printJobCanceled(PrintJobEvent pje) {
        allDone();
      }

      @Override
      public void printJobCompleted(PrintJobEvent pje) {
        allDone();
      }

      @Override
      public void printJobFailed(PrintJobEvent pje) {
        allDone();
      }

      @Override
      public void printJobNoMoreEvents(PrintJobEvent pje) {
        allDone();
      }

      void allDone() {
        synchronized (PrintJobWatcher.this) {
          done = true;
          PrintJobWatcher.this.notify();
        }
      }
    });
  }

  public synchronized void waitForDone() {
    try {
      while (!done) {
        wait();
      }
    } catch (InterruptedException e) {
    }
  }
}