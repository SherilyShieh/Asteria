package com.sherily.shieh.asteria.model;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.sherily.shieh.asteria.baidumaputils.GeoCoderHelper;

/**
 * Created by Administrator on 2016/6/14.
 */
public class AddressModel {
    private LatLng latLng;
    private ReverseGeoCodeResult result;
    private String name;
    private String adress;
    private String province;
    private String city;
    private String district;
    private String street;
    private String streetNum;
//    private GeoCoderHelper geoCoderHelper;
//    private static AddressModel addressModel = null;
//
//    public static AddressModel getAddressModel(LatLng latLng, ReverseGeoCodeResult result) {
//
//        addressModel = new AddressModel(latLng,result);
//        return addressModel;
//    }
//
//    public void reverseGeoCod() {
//        geoCoderHelper = new GeoCoderHelper();
//        geoCoderHelper.setLatLng(latLng);
//        geoCoderHelper.reverseGeoCode();
//        geoCoderHelper.setListener(new GeoCoderHelper.onReverseGeoCodeResultListener() {
//            @Override
//            public void result(Object obj, LatLng latLng) {
//                ReverseGeoCodeResult reverseGeoCodeResult = (ReverseGeoCodeResult) obj;
//                addressModel.setAdress(((ReverseGeoCodeResult) obj).getAddress());
//                addressModel.setProvince(((ReverseGeoCodeResult) obj).getAddressDetail().province);
//                addressModel.setCity(((ReverseGeoCodeResult) obj).getAddressDetail().city);
//                addressModel.setDistrict(((ReverseGeoCodeResult) obj).getAddressDetail().district);
//                addressModel.setStreet(((ReverseGeoCodeResult) obj).getAddressDetail().street);
//                addressModel.setStreetNum(((ReverseGeoCodeResult) obj).getAddressDetail().streetNumber);
//            }
//        });
//    }


    public ReverseGeoCodeResult getResult() {
        return result;
    }

    public void setResult(ReverseGeoCodeResult result) {
        this.result = result;
    }

    public void setModel() {
        setAdress(result.getAddress());
        setProvince(result.getAddressDetail().province);
        setCity(result.getAddressDetail().city);
        setDistrict(result.getAddressDetail().district);
        setStreet(result.getAddressDetail().street);
        setStreetNum(result.getAddressDetail().streetNumber);
    }

    public AddressModel(LatLng latLng, ReverseGeoCodeResult result) {
        this.latLng = latLng;
        this.result = result;
    }

    public String getStreetNum() {
        return streetNum;
    }

    private void setStreetNum(String streetNum) {
        this.streetNum = streetNum;
    }

    public String getStreet() {
        return street;
    }

    private void setStreet(String street) {
        this.street = street;
    }

    public String getDistrict() {
        return district;
    }

    private void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    private void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    private void setProvince(String province) {
        this.province = province;
    }

    public String getAdress() {
        return adress;
    }

    private void setAdress(String adress) {
        this.adress = adress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
