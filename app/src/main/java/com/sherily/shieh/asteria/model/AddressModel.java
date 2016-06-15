package com.sherily.shieh.asteria.model;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.sherily.shieh.asteria.baidumaputils.GeoCoderHelper;

/**
 * Created by Administrator on 2016/6/14.
 */
public class AddressModel {
    public LatLng latLng;
    //private ReverseGeoCodeResult result;
    public String poiName;
    public String poiAddress;
    public String resultAdress;
    public String province;
    public String city;
    public String district;
    public String street;
    public String streetNum;

    public AddressModel(LatLng latLng, String poiName, String poiAddress, String resultAdress, String province, String city, String district, String street, String streetNum) {
        this.latLng = latLng;
        this.poiName = poiName;
        this.poiAddress = poiAddress;
        this.resultAdress = resultAdress;
        this.province = province;
        this.city = city;
        this.district = district;
        this.street = street;
        this.streetNum = streetNum;
    }


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


//    public ReverseGeoCodeResult getResult() {
//        return result;
//    }
//
//    public void setResult(ReverseGeoCodeResult result) {
//        this.result = result;
//    }
//
//    public void setModel() {
//        setAdress(result.getAddress());
//        setProvince(result.getAddressDetail().province);
//        setCity(result.getAddressDetail().city);
//        setDistrict(result.getAddressDetail().district);
//        setStreet(result.getAddressDetail().street);
//        setStreetNum(result.getAddressDetail().streetNumber);
//    }
//
//    public AddressModel(LatLng latLng, ReverseGeoCodeResult result) {
//        this.latLng = latLng;
//        this.result = result;
//    }

}
