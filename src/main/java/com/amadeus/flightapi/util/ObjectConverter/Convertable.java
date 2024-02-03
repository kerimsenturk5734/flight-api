package com.amadeus.flightapi.util.ObjectConverter;

public interface Convertable <Input, Output>{
    Output convert(Input input);
    Input deConvert(Output output);
}
