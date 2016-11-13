package com.nanodegree.pankaj.popularmoviesapp.model;

import java.util.List;


public class MovieTrailers {
    private Number id;
    private List<MovieTrailer> results;

    public Number getId(){
        return this.id;
    }
    public void setId(Number id){
        this.id = id;
    }
    public List<MovieTrailer> getResults(){
        return this.results;
    }
    public void setResults(List<MovieTrailer> results){
        this.results = results;
    }
}
