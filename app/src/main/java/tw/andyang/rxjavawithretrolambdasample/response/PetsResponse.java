package tw.andyang.rxjavawithretrolambdasample.response;

import tw.andyang.rxjavawithretrolambdasample.model.Pet;
import tw.andyang.rxjavawithretrolambdasample.model.Result;

public class PetsResponse {
    
    private Result<Pet> result;

    public Result<Pet> getResult() {
        return result;
    }
}
