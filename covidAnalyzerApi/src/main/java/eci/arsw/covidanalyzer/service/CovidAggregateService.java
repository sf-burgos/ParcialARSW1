package eci.arsw.covidanalyzer.service;

import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.UUID.randomUUID;
@Service
public class CovidAggregateService implements ICovidAggregateService {
    private List<Result> lista = new CopyOnWriteArrayList<>();

    public CovidAggregateService(){
        lista.add(new Result (ResultType.TRUE_POSITIVE,randomUUID(),"BRAYAN", "Burgos", "Masculino", "brayan@mail.escuelaing.edu.co", "17-NOV-2020", "true", true, 2.0 ));
    }
    @Override
    public boolean aggregateResult(Result result, ResultType type) {
        if (result.getResultType()==type){
            lista.add(result);

        }


        return true;
    }

    @Override
    public List<Result> getResult(ResultType type) {
        List<Result> lista2 = new CopyOnWriteArrayList<>();
        for (Result x: lista){
            if (x.getResultType()==type){
                lista2.add(x);
            }

        }
        return lista2;
    }

    @Override
    public void upsertPersonWithMultipleTests(UUID id, ResultType type) {

    }
}
