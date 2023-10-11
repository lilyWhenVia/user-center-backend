package com.catGod.model.request;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by lily via on 2023/9/20 16:59
 */
@Data
public class requestUser implements Serializable {

    Serializable serialVersionUID = 122L;
    public String userId;
    public String userAccount;
    public String username;
    public String userPassword;
    public String checkPassword;
    public String planetCode;

}
