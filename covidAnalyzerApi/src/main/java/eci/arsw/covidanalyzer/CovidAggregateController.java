package eci.arsw.covidanalyzer;

import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;
import eci.arsw.covidanalyzer.service.CovidAggregateService;
import eci.arsw.covidanalyzer.service.ICovidAggregateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping (value = "/covid/result")
public class CovidAggregateController {

    @Autowired
    CovidAggregateService covidAggregateService;


    //@RequestMapping(value = "/covid/result/true-positive", method = RequestMethod.POST)
    //public ResponseEntity<?> addTruePositiveResult(Result result) {
        //return new ResponseEntity<>(covidAggregateService.getResult(), HttpStatus.ACCEPTED);
    //}

    //TODO: Implemente todos los metodos GET que hacen falta.

    @RequestMapping(value = "/true-positive", method = RequestMethod.GET)
    public ResponseEntity<?> manejadorRecursoCOVID() {

            List<Result> data = covidAggregateService.getResult(ResultType.TRUE_POSITIVE);
            if (data == null) {
                return new ResponseEntity<>("Error 404",HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(data,HttpStatus.ACCEPTED);
            }


        }

    @RequestMapping(value="/agregar", method = RequestMethod.POST)
    public ResponseEntity<?> manejadorPostRecursoName(@RequestBody Result result) {
        covidAggregateService.aggregateResult(result, ResultType.TRUE_POSITIVE);
        return new ResponseEntity<>(HttpStatus.CREATED);


    }


    @RequestMapping(value = "/persona/{id}", method = RequestMethod.PUT)
    public ResponseEntity savePersonaWithMultipleTests() {
        //TODO
        covidAggregateService.getResult(ResultType.TRUE_POSITIVE);
        return null;
    }
    
}