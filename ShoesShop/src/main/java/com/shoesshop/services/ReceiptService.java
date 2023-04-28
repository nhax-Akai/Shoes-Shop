package com.shoesshop.services;

import com.shoesshop.models.Receipt;
import com.shoesshop.repositories.ReceiptRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReceiptService {
    @Autowired
    ReceiptRepos receiptRepos;

    public Optional<Receipt> getById(Long id){
        return receiptRepos.findById(id);
    }

    public void save(Receipt receipt){
        receiptRepos.save(receipt);
    }

    public List<Receipt> getOrder(){
        List<Receipt> receiptList = new ArrayList<>();
        for(Receipt receipt: receiptRepos.findAll()){
            if(!receipt.isDelivered()){
                receiptList.add(receipt);
            }
        }
        return receiptList;
    }

    public List<Receipt> getOrderedToday(){
        List<Receipt> receiptList = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String time = dateFormat.format(date).split(" ")[0];
        for(Receipt receipt : receiptRepos.findAll()){
            if(receipt.isDelivered() && Objects.equals(receipt.getUpdateAt().split(" ")[0], time)){
                receiptList.add(receipt);
            }
        }
        return receiptList;
    }
}
