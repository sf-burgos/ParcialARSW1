package eci.arsw.covidanalyzer;

import com.sun.deploy.security.SelectableSecurityManager;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A Camel Application
 */
public abstract class CovidAnalyzerTool implements Runnable {
    //Variable para cambiar el numero de hilos
    private ResultAnalyzer resultAnalyzer;
    private TestReader testReader;
    private int amountOfFilesTotal;
    private AtomicInteger amountOfFilesProcessed;

    public CovidAnalyzerTool() {
        resultAnalyzer = new ResultAnalyzer();
        testReader = new TestReader();
        amountOfFilesProcessed = new AtomicInteger();
    }

    public void processResultData() {
        amountOfFilesProcessed.set(0);
        List<File> resultFiles = getResultFileList();
        amountOfFilesTotal = resultFiles.size();
        for (File resultFile : resultFiles) {
            List<Result> results = testReader.readResultsFromFile(resultFile);
            for (Result result : results) {
                resultAnalyzer.addResult(result);
            }
            amountOfFilesProcessed.incrementAndGet();
        }
    }

    private List<File> getResultFileList() {
        List<File> csvFiles = new ArrayList<>();
        try (Stream<Path> csvFilePaths = Files.walk(Paths.get("src/main/resources/")).filter(path -> path.getFileName().toString().endsWith(".csv"))) {
            csvFiles = csvFilePaths.map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvFiles;
    }


    public Set<Result> getPositivePeople() {
        return resultAnalyzer.listOfPositivePeople();
    }

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        /**
         while (true) {
         Scanner scanner = new Scanner(System.in);
         String line = scanner.nextLine();
         if (line.contains("exit"))
         break;
         String message = "Processed %d out of %d files.\nFound %d positive people:\n%s";
         Set<Result> positivePeople = covidAnalyzerTool.getPositivePeople();
         String affectedPeople = positivePeople.stream().map(Result::toString).reduce("", (s1, s2) -> s1 + "\n" + s2);
         message = String.format(message, covidAnalyzerTool.amountOfFilesProcessed.get(), covidAnalyzerTool.amountOfFilesTotal, positivePeople.size(), affectedPeople);
         System.out.println(message);
         }
         }
         */
        Integer NTHERADS = 5;// variable para poder dividir la funcion en numero de hilos
        CovidAnalyzerTool covidAnalyzerTool = new CovidAnalyzerTool() {
            @Override
            public void run() {

            }
        };
        Thread processingThread = new Thread(() -> covidAnalyzerTool.processResultData());
        List<File> resultFileList = covidAnalyzerTool.getResultFileList();
        processingThread.start();
        Integer indiceInferior = 0;
        List<Thread> threadsSolucion = new ArrayList<>();



        if (resultFileList.size() % NTHERADS != 0) {
            for (int i = 0; i < NTHERADS - 1; i++) {
                List<File> filesDivision = covidAnalyzerTool.divideFiles(resultFileList, indiceInferior, indiceInferior + NTHERADS);
                System.out.println(indiceInferior + "---" + (indiceInferior + NTHERADS - 1));
                threadsSolucion.add(new CovidThread(filesDivision));
                indiceInferior = indiceInferior + NTHERADS;
            }
            System.out.println(indiceInferior + "---" + (resultFileList.size() - 1));
            List<File> filesDivision = covidAnalyzerTool.divideFiles(resultFileList, indiceInferior, resultFileList.size());
            threadsSolucion.add(new CovidThread(filesDivision));
        } else {
            for (int i = 0; i < NTHERADS; i++) {
                List<File> filesDivision = covidAnalyzerTool.divideFiles(resultFileList, indiceInferior, indiceInferior + NTHERADS);
                threadsSolucion.add(new CovidThread(filesDivision));
                indiceInferior = indiceInferior + NTHERADS;
            }
        }


}

    private List<File> divideFiles(List<File> resultFileList, Integer indiceInferior, int size) {
        return resultFileList.subList(indiceInferior, size-1);
    }


}

