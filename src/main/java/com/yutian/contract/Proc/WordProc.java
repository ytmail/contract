package com.yutian.contract.Proc;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.*;

import com.aspose.words.Bookmark;
import com.aspose.words.Cell;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.FindReplaceOptions;
import com.aspose.words.License;
import com.aspose.words.Node;
import com.aspose.words.NodeType;
import com.aspose.words.Row;
import com.aspose.words.Run;
import com.aspose.words.Table;


import com.aspose.words.SaveFormat;

public class WordProc {

    Document document;

    public WordProc(InputStream stream) {
        Load(stream);
    }

    public WordProc(String filename) {
        Load(filename);
    }

    private void Load(InputStream stream) {
        InputStream is1 = WordProc.class.getClassLoader().getResourceAsStream("license.xml");
        License aposeLic = new License();
        try {
            aposeLic.setLicense(is1);
            is1.close();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            document = new Document(stream);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void Load(String filename) {
        FileInputStream is;
        try {
            is = new FileInputStream(filename);
            Load(is);
            is.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void Save(String filename, int saveformat) {
        try {
            document.save(filename, saveformat);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void SaveAsPDF(String filename) {
        Save(filename, SaveFormat.PDF);
    }

    public ByteArrayOutputStream Save(int saveformat) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            document.save(os, saveformat);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return os;
    }

    public ByteArrayOutputStream SaveAsPDF() {
        return Save(SaveFormat.PDF);
    }

    public Map<String,List<String>> GetBookMarksTableTitles()
    {
        Map<String,List<String>> map = new HashMap<String,List<String>>();
        for (Bookmark bookmark : document.getRange().getBookmarks()) {
            Table table=FindTable(bookmark.getName());
            if (table!=null)
            {
                map.put(bookmark.getName(),GetTableTitles(table));
            }
        }
        return map;
    }
    
    public List<String> GetDocPatternList()
    {
        String s = document.getText();
        String pattern="%.*?%";
        Pattern r = Pattern.compile(pattern);
        Matcher m =r.matcher( s);
        List<String> list = new ArrayList<String>();
        while (m.find())
        {
             list.add(m.group().replace("%", ""));
        }
        return list;
    }

    private Table FindTable(String bookname) {
        DocumentBuilder db = new DocumentBuilder(document);
        try {
            db.moveToBookmark(bookname);
        } catch (Exception ce) {
            return null;
        }
        Node T = db.getCurrentNode();
        if (T.getNodeType() == NodeType.TABLE)
            return (Table) T;
        else {
            T = T.getParentNode();
            while (T.getNodeType() != NodeType.TABLE && T.getNodeType() != NodeType.DOCUMENT) {
                T = T.getParentNode();
            }
            if (T.getNodeType() == NodeType.TABLE)
                return (Table) T;
            else
                return null;
        }
    }

    private void ReplaceRow(Row row, Map<String, String> map) {
        for (Cell cell : row) {
            String value = map.get(cell.getFirstParagraph().getRuns().get(0).getText());
            cell.getFirstParagraph().getRuns().get(0).setText(value);
        }
    }

    private Row FindTitleRow(Table table, Map<String, String> map) {
        for (Row row : table) {
            for (int i = 0; i < row.getCells().getCount(); i++) {
                Run run = row.getCells().get(i).getFirstParagraph().getRuns().get(0);
                if (run == null || map.get(run.getText()) == null)
                    break;
                if (i == row.getCells().getCount() - 1)
                    return row;
            }
        }
        return null;
    }

    public List<String> GetTableTitles(Table table) {
        List<String> list = new ArrayList<String>();
        Row row = table.getFirstRow();
        for (int i = 0; i < row.getCells().getCount(); i++) {
            Run run = row.getCells().get(i).getFirstParagraph().getRuns().get(0);
            if (run != null)
                list.add(run.getText());
        }
        return list;
    }

    private void AddRows(Table table, List<Map<String, String>> valuelist) {

        Row titlerow = FindTitleRow(table, valuelist.get(0));
        Row InsertPosRow = titlerow;
        for (Map<String, String> map : valuelist) {
            Row clonerow = (Row) titlerow.deepClone(true);
            ReplaceRow(clonerow, map);
            table.insertAfter(clonerow, InsertPosRow);
            InsertPosRow = clonerow;

        }
    }

    public void ReplaceTable(String bookmarkname, List<Map<String, String>> valuelist) {
        Table table = FindTable(bookmarkname);
        AddRows(table, valuelist);
    }

    public void ReplaceText(Map<String, String> map) {

        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                document.getRange().replace(entry.getKey(), entry.getValue(), new FindReplaceOptions());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }

}