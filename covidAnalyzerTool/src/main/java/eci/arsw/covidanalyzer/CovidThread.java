package eci.arsw.covidanalyzer;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CovidThread extends Thread  {
    private  final int  NTHERADS =5;
    //Variable para cambiar el numero de hilos
    private ResultAnalyzer resultAnalyzer;
    private TestReader testReader;
    private int amountOfFilesTotal;
    private AtomicInteger amountOfFilesProcessed;
    private boolean pause;
    private List<File> files;

        public CovidThread(List<File> files ){
            this.files = files;
            this.resultAnalyzer = resultAnalyzer;
            this.testReader = testReader;
            this.amountOfFilesTotal = amountOfFilesTotal;
            this.pause = false;


        }

    @Override
    public void run(){
            for (File resultFile : this.files){
                System.out.println("here");
                List<Result> results = testReader.readResultsFromFile(resultFile);
                for (Result result: results){
                    resultAnalyzer.addResult(result);
                }
            }

    }
}
