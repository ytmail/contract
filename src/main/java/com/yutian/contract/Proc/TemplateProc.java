package com.yutian.contract.Proc;

import java.io.ByteArrayInputStream;

import java.io.InputStream;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import com.yutian.contract.entity.Contracts;
import com.yutian.contract.entity.FieldType;
import com.yutian.contract.entity.Options;
import com.yutian.contract.entity.TextReplacePattern;
import com.yutian.contract.mapper.ContractsMapper;
import com.yutian.contract.mapper.OptionsMapper;
import com.yutian.contract.mapper.TextReplacePatternMapper;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateProc {

    private WordProc wordproc;
    private Contracts contract;

    @Autowired
    private ContractsMapper contractmapper;
    @Autowired
    private TextReplacePatternMapper textreplacepatternmapper;
    @Autowired
    private OptionsMapper optionsmapper;

    public FieldType AutoSetTypeFromName(String name) {
        if (name.indexOf("日期") >= 0 || name.indexOf("日") == name.length() - 1) {
            return FieldType.Date;
        }
        if (name.indexOf("金额") >= 0 || name.indexOf("额度") >= 0 || name.indexOf("本金") >= 0 || name.indexOf("价格") >= 0
                || name.indexOf("价值") == name.length() - 2 || name.indexOf("费") >= name.length() - 2
                || name.indexOf("单价") >= name.length() - 2 || name.indexOf("合计") >= name.length() - 2) {
            return FieldType.Decimal;
        }
        if (name.indexOf("序号") >= 0)
            return FieldType.AutoNumber;
        if (name.indexOf("数量") >= 0)
            return FieldType.Numbering;
        if (name.indexOf("手机号") >= 0 || name.indexOf("电话号码") >= 0) {
            return FieldType.PhoneNumber;
        }
        if (name.indexOf("身份证") >= 0 || name.indexOf("证件号") >= 0)
            return FieldType.IDCard;
        if (name.indexOf("邮政编码") >= 0 || name.indexOf("邮编") >= 0) {
            return FieldType.PostCode;
        }

        return FieldType.Text;
    }

    public List<TextReplacePattern> GetTextReplacePattern() {
        List<TextReplacePattern> patterns = new ArrayList<TextReplacePattern>();
        List<String> list = wordproc.GetDocPatternList();
        for (String s : list) {
            TextReplacePattern pattern = new TextReplacePattern();
            pattern.setContract_id(this.contract.getId());
            if (s.indexOf(">") >= 0 || s.indexOf("#") >= 0) {
                if (s.indexOf(">") >= 0) {
                    pattern.setName(s.split(">")[0]);
                    pattern.setType(FieldType.List.value());
                } else {
                    pattern.setName(s.split("#")[0]);
                    pattern.setType(FieldType.Option.value());
                }
                textreplacepatternmapper.AddPattern(pattern);
                String options = s.substring(s.indexOf(">") + 1);
                String[] optionlist = options.split(",");
                List<Options> Field_Options = new ArrayList<Options>();
                for (String os : optionlist) {
                    Options option = new Options();
                    option.setParentfield_id(pattern.getId());
                    option.setText(os);
                    option.setType(AutoSetTypeFromName(os).value());
                    optionsmapper.AddOption(option);
                    Field_Options.add(option);
                }
                pattern.Field_Options = Field_Options;
            } else {
                pattern.setName(s);
                pattern.setType(AutoSetTypeFromName(s).value());
                textreplacepatternmapper.AddPattern(pattern);
            }
            patterns.add(pattern);
        }
        return patterns;
    }

    public List<TextReplacePattern> GetTablePattern() {
        List<TextReplacePattern> patterns = new ArrayList<TextReplacePattern>();
        Map<String, List<String>> list = wordproc.GetBookMarksTableTitles();
        for (Map.Entry<String, List<String>> entry : list.entrySet()) {
            TextReplacePattern pattern = new TextReplacePattern();
            pattern.setContract_id(this.contract.getId());
            pattern.setType(FieldType.Table.value());
            pattern.setName(entry.getKey());
            List<Options> Field_Options = new ArrayList<Options>();
            textreplacepatternmapper.AddPattern(pattern);
            for (String os : entry.getValue()) {
                Options option = new Options();
                option.setParentfield_id(pattern.getId());
                option.setText(os);
                option.setType(AutoSetTypeFromName(os).value());
                optionsmapper.AddOption(option);
                Field_Options.add(option);
            }
            pattern.Field_Options = Field_Options;
            patterns.add(pattern);
        }
        return patterns;
    }

    public void AddContract(Contracts contract) {
        try {

            contractmapper.AddContract(contract);
            this.contract = contract;
            InputStream is = new ByteArrayInputStream(contract.getTemplate());
            wordproc = new WordProc(is);
            List<TextReplacePattern> list = GetTextReplacePattern();
            list.addAll(GetTablePattern());
            contract.PatternList = list;
            is.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public Contracts LoadTemplate(int id) {
        Contracts contract = contractmapper.GetContract(id);
        contract.PatternList = textreplacepatternmapper.GetPatternByContractID(id);
        for (TextReplacePattern pattern : contract.PatternList) {
            pattern.Field_Options = optionsmapper.GetFieldOptions(pattern.getId());
        }
        return contract;
    }

    public int DeleteOption(int id) {
        return optionsmapper.DeleteOption(id);
    }


    public int DeletePattern(int id) {
        optionsmapper.DeleteOptionByParentfieldID(id);
        return textreplacepatternmapper.DeletePattern(id);
    }

    public int DeleteContract(int id) {
        List<TextReplacePattern> plist = textreplacepatternmapper.GetPatternByContractID(id);
        for (TextReplacePattern textReplacePattern : plist) {
            optionsmapper.DeleteOptionByParentfieldID(textReplacePattern.getId());
        }
        textreplacepatternmapper.DeletePatternByContractID(id);
        return contractmapper.DeleteContract(id);
    }

}
