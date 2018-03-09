package com.wf.gts.manage.controller;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wf.gts.common.beans.TxTransactionItem;
import com.wf.gts.common.enums.TransactionRoleEnum;
import com.wf.gts.common.enums.TransactionStatusEnum;
import com.wf.gts.manage.service.TxManagerService;


@Controller
@RequestMapping("/gtsManage")
public class TxManagerIndexController {
  
    @Autowired
    private final TxManagerService txManagerService; 
    
    @Autowired
    public TxManagerIndexController(TxManagerService txManagerService) {
        this.txManagerService=txManagerService;
    }

   
    
    @RequestMapping("/tx/list")
    @ResponseBody
    public Object list(HttpServletRequest request) { 
    	List<List<TxTransactionItem>> list = txManagerService.listTxTransactionItem(); 
    	list.stream().forEach(group->{
    		group.stream().forEach(item ->{
    			if(TransactionStatusEnum.PRE_COMMIT.getCode() == item.getStatus()){
    				item.setStatusValue(TransactionStatusEnum.PRE_COMMIT.getDesc());
    			}else if(TransactionStatusEnum.BEGIN.getCode() == item.getStatus()){
    				item.setStatusValue(TransactionStatusEnum.BEGIN.getDesc());
    			}else if(TransactionStatusEnum.COMMIT.getCode() == item.getStatus()){
    				item.setStatusValue(TransactionStatusEnum.COMMIT.getDesc());
    			}else if(TransactionStatusEnum.ROLLBACK.getCode() == item.getStatus()){
    				item.setStatusValue(TransactionStatusEnum.ROLLBACK.getDesc());
    			}
    			
    			if(TransactionRoleEnum.START.getCode() == item.getRole()){
    				item.setRoleValue(TransactionRoleEnum.START.getDesc());
    			}else if(TransactionRoleEnum.ACTOR.getCode() == item.getRole()){
    				item.setRoleValue(TransactionRoleEnum.ACTOR.getDesc());
    			}
    		});
    		
    	});
        return list;
    }

}
