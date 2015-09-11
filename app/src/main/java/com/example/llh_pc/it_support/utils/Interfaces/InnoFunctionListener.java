package com.example.llh_pc.it_support.utils.Interfaces;

/**
 * Created by Khanh Vo on 9/7/2015.
 * Email: khanhvo@innoria.com || idkhanhvo272@gmail.com
 * Phone number: 093 28 11 291
 */
public interface InnoFunctionListener {
    /**
     * Init all variable for Flags
     */
    public void initFlags();

    /**
     * Init all control of View
     */
    public void initControl();

    /**
     * set all event if have for control
     */
    public void setEventForControl();

    /**
     * get Data to show on the view
     */
    public void getData(String... params);

    /**
     * set Data for every control if have
     */
    public void setData();
}
