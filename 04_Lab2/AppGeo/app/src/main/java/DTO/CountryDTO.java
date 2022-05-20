package DTO;

import java.io.Serializable;

public class CountryDTO implements Serializable {
    private String continent;
    private String continentName;
    private String capital;
    private String languages;
    private String geonameId;
    private String south;
    private String isoAlpha3;
    private String north;
    private String isoNumeric;
    private String east;
    private String west;
    private String countryCode;
    private String countryName;
    private String areaInSqKm;
    private String currencyCode;
    private String population;
    private String flagUrl;
    private String map;

    public CountryDTO() {

    }

    public CountryDTO(String continent, String continentName, String capital, String languages, String geonameId, String south, String isoAlpha3, String north, String isoNumeric, String east, String west, String countryCode, String countryName, String areaInSqKm, String currencyCode, String population, String flagUrl) {
        this.continent = continent;
        this.capital = capital;
        this.languages = languages;
        this.geonameId = geonameId;
        this.continentName = continentName;
        this.south = south;
        this.isoAlpha3 = isoAlpha3;
        this.north = north;
        this.isoNumeric = isoNumeric;
        this.east = east;
        this.west = west;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.areaInSqKm = areaInSqKm;
        this.currencyCode = currencyCode;
        this.population = population;
        this.flagUrl = flagUrl;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(String geonameId) {
        this.geonameId = geonameId;
    }

    public String getSouth() {
        return south;
    }

    public void setSouth(String south) {
        this.south = south;
    }

    public String getIsoAlpha3() {
        return isoAlpha3;
    }

    public void setIsoAlpha3(String isoAlpha3) {
        this.isoAlpha3 = isoAlpha3;
    }

    public String getNorth() {
        return north;
    }

    public void setNorth(String north) {
        this.north = north;
    }

    public String getIsoNumeric() {
        return isoNumeric;
    }

    public void setIsoNumeric(String isoNumeric) {
        this.isoNumeric = isoNumeric;
    }

    public String getEast() {
        return east;
    }

    public void setEast(String east) {
        this.east = east;
    }

    public String getWest() {
        return west;
    }

    public void setWest(String west) {
        this.west = west;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getAreaInSqKm() {
        return areaInSqKm;
    }

    public void setAreaInSqKm(String areaInSqKm) {
        this.areaInSqKm = areaInSqKm;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }

    @Override
    public String toString() {
        return "CountryDTO{" +
                "continent='" + continent + '\'' +
                ", continentName='" + continentName + '\'' +
                ", capital='" + capital + '\'' +
                ", languages='" + languages + '\'' +
                ", geonameId='" + geonameId + '\'' +
                ", south='" + south + '\'' +
                ", isoAlpha3='" + isoAlpha3 + '\'' +
                ", north='" + north + '\'' +
                ", isoNumeric='" + isoNumeric + '\'' +
                ", east='" + east + '\'' +
                ", west='" + west + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", countryName='" + countryName + '\'' +
                ", areaInSqKm='" + areaInSqKm + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", population='" + population + '\'' +
                ", flagUrl='" + flagUrl + '\'' +
                ", map='" + map + '\'' +
                '}';
    }
}
