package com.yutian.contract.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayOutputStream;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.yutian.contract.Proc.TemplateProc;
import com.yutian.contract.Proc.WordProc;
import com.yutian.contract.entity.Contracts;
import com.yutian.contract.mapper.ContractsMapper;


@Controller
public class HomeController {
    @Autowired
      TemplateProc proc;

    @RequestMapping(value="/gettemplate",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String get_template(int id)
    {
        Contracts contract=proc.LoadTemplate(id);
        String s=JSON.toJSONString(contract);
        return s;

    }
    @RequestMapping(value = "/")
    public void ReadDoc(HttpServletResponse response) {
        Map<String, String> content = new HashMap<String, String>();
        content.put("ID", "1");
        content.put("ACCT","87110011111122");
        content.put("ORG","8710002");
        content.put("MANAGER","87100138");
        content.put("BAL","10000.00");
        content.put("BAL_AVG","9999.99");

        Map<String, String> content2 = new HashMap<String, String>();
        content2.put("ID", "2");
        content2.put("ACCT","87110011111123");
        content2.put("ORG","8710003");
        content2.put("MANAGER","87100148");
        content2.put("BAL","20000.00");
        content2.put("BAL_AVG","19999.99");

        Map<String,String> TT = new HashMap<String,String>();
        TT.put("民主革命", "你好啊！替换成功！");
        Contracts contract;
        int id=0;
        try {
/*             FileInputStream is = new FileInputStream("C:\\Users\\yutian\\Documents\\1.docx");
          
            contract.setCreator("马俊雷");
            contract.setCreatedate(new Date());
            contract.setTemplate(is.readAllBytes());
            contract.setName("第一次测试");
       
            proc.AddContract(contract); */
            contract=proc.LoadTemplate(20);

            System.out.println(id);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        List<Map<String, String>> t = new ArrayList<Map<String,String>>();
        t.add(content);
        t.add(content2);
        try {

           
           WordProc wordproc=new WordProc("C:\\Users\\yutian\\Documents\\1.docx");
           wordproc.ReplaceTable("TEST", t);
           wordproc.ReplaceText(TT);
           ByteArrayOutputStream out=wordproc.SaveAsPDF();
           response.setContentType("application/pdf");
           response.getOutputStream().write(out.toByteArray());

        }

        catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }


}
