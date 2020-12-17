package com.xxs.result;

public class RoleEntity extends ResponseEntity {

    private boolean result = true;
    private String msg = "操作成功";
    private Object data;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static RoleEntity success(){
        return new RoleEntity();
    }

    public static RoleEntity success(String msg){
        RoleEntity entity = new RoleEntity();
        entity.msg = msg;
        return entity;
    }

    public static RoleEntity success(Object data){
        RoleEntity entity = new RoleEntity();
        entity.data = data;
        return entity;
    }

    public static RoleEntity success(String msg, Object data){
        RoleEntity entity = new RoleEntity();
        entity.msg = msg;
        entity.data = data;
        return entity;
    }

    public static RoleEntity error(){
        RoleEntity entity = new RoleEntity();
        entity.result = false;
        entity.msg = "操作失败";
        return entity;
    }

    public static RoleEntity error(String msg){
        RoleEntity entity = new RoleEntity();
        entity.result = false;
        entity.msg = msg;
        return entity;
    }

    public static RoleEntity error(Object data){
        RoleEntity entity = new RoleEntity();
        entity.result = false;
        entity.data = data;
        return entity;
    }

    public static RoleEntity error(String msg, Object data){
        RoleEntity entity = new RoleEntity();
        entity.result = false;
        entity.msg = msg;
        entity.data = data;
        return entity;
    }
}
