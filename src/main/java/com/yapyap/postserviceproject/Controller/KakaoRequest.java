package com.yapyap.postserviceproject.Controller;

import java.util.ArrayList;

public class KakaoRequest {
    Intent IntentObject;
    UserRequest UserRequestObject;
    ArrayList< Object > contexts = new ArrayList < Object > ();
    Bot BotObject;
    Action ActionObject;


    // Getter Methods

    public Intent getIntent() {
        return IntentObject;
    }

    public UserRequest getUserRequest() {
        return UserRequestObject;
    }

    public Bot getBot() {
        return BotObject;
    }

    public Action getAction() {
        return ActionObject;
    }

    // Setter Methods

    public void setIntent(Intent intentObject) {
        this.IntentObject = intentObject;
    }

    public void setUserRequest(UserRequest userRequestObject) {
        this.UserRequestObject = userRequestObject;
    }

    public void setBot(Bot botObject) {
        this.BotObject = botObject;
    }

    public void setAction(Action actionObject) {
        this.ActionObject = actionObject;
    }
}
 class Action {
    private String name;
    private String clientExtra;
    Params ParamsObject;
    private String id;
    DetailParams DetailParamsObject;


    // Getter Methods

    public String getName() {
        return name;
    }

    public String getClientExtra() {
        return clientExtra;
    }

    public Params getParams() {
        return ParamsObject;
    }

    public String getId() {
        return id;
    }

    public DetailParams getDetailParams() {
        return DetailParamsObject;
    }

    // Setter Methods

    public void setName(String name) {
        this.name = name;
    }

    public void setClientExtra(String clientExtra) {
        this.clientExtra = clientExtra;
    }

    public void setParams(Params paramsObject) {
        this.ParamsObject = paramsObject;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDetailParams(DetailParams detailParamsObject) {
        this.DetailParamsObject = detailParamsObject;
    }
}
 class DetailParams {


    // Getter Methods



    // Setter Methods


}
 class Bot {
    private String id;
    private String name;


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
 class UserRequest {
    private String timezone;
    Params ParamsObject;
    Block BlockObject;
    private String utterance;
    private String lang;
    User UserObject;


    // Getter Methods

    public String getTimezone() {
        return timezone;
    }

    public Params getParams() {
        return ParamsObject;
    }

    public Block getBlock() {
        return BlockObject;
    }

    public String getUtterance() {
        return utterance;
    }

    public String getLang() {
        return lang;
    }

    public User getUser() {
        return UserObject;
    }

    // Setter Methods

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public void setParams(Params paramsObject) {
        this.ParamsObject = paramsObject;
    }

    public void setBlock(Block blockObject) {
        this.BlockObject = blockObject;
    }

    public void setUtterance(String utterance) {
        this.utterance = utterance;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setUser(User userObject) {
        this.UserObject = userObject;
    }
}
 class User {
    private String id;
    private String type;
    Properties PropertiesObject;


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Properties getProperties() {
        return PropertiesObject;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setProperties(Properties propertiesObject) {
        this.PropertiesObject = propertiesObject;
    }
}
 class Properties {
    private String appUserId;
    private String appUserStatus;
    private String plusfriend_user_key;


    // Getter Methods

    public String getAppUserId() {
        return appUserId;
    }

    public String getAppUserStatus() {
        return appUserStatus;
    }

    public String getPlusfriend_user_key() {
        return plusfriend_user_key;
    }

    // Setter Methods

    public void setAppUserId(String appUserId) {
        this.appUserId = appUserId;
    }

    public void setAppUserStatus(String appUserStatus) {
        this.appUserStatus = appUserStatus;
    }

    public void setPlusfriend_user_key(String plusfriend_user_key) {
        this.plusfriend_user_key = plusfriend_user_key;
    }
}
class Block {
    private String id;
    private String name;


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
 class Params {
    private String exampleParam;


    // Getter Methods

    public String getExampleParam() {
        return exampleParam;
    }

    // Setter Methods

    public void setExampleParam(String exampleParam) {
        this.exampleParam = exampleParam;
    }
}
class Intent {
    private String id;
    private String name;


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}