import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadExcelClass {

    //文件名
    private String excelPath;
    //sheet坐标
    private Integer sheetIndex;
    //标题行
    private Integer headerRow;
    //数据列个数
    private Integer colNum;
    //数据起始行列
    private Integer startRow;
    private Integer startCol;
    //excel图片存储临时目录
    private String tmpDir;

    public ReadExcelClass(String excelPath, Integer sheetIndex, Integer headerRow, Integer colNum, Integer startRow, Integer startCol, String tmpDir) {
        this.excelPath = excelPath;
        this.sheetIndex = sheetIndex;
        this.headerRow = headerRow;
        this.colNum = colNum;
        this.startRow = startRow;
        this.startCol = startCol;
        this.tmpDir = tmpDir;
    }

    public Integer getSheetIndex() {
        return sheetIndex;
    }

    public Integer getHeaderRow() {
        return headerRow;
    }

    public Integer getColNum() {
        return colNum;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public Integer getStartCol() {
        return startCol;
    }

    public String getExcelPath() {
        return excelPath;
    }

    public String getTmpDir() {
        return tmpDir;
    }

    public void updateSheetInfo(Integer sheetIndex, Integer headerRow, Integer colNum, Integer startRow, Integer startCol, String tmpDir){
        this.sheetIndex = sheetIndex;
        this.headerRow = headerRow;
        this.colNum = colNum;
        this.startRow = startRow;
        this.startCol = startCol;
        this.tmpDir = tmpDir;
    }

    public Map<String, List<String>> getDataFormExcel() throws Exception {
        if (excelPath == null || (!excelPath.endsWith(".xls") && !excelPath.endsWith(".xlsx"))){
            throw new Exception("输入文件是null或文件不是Excel文件");
        }

        //定义文件输入流、工作簿、sheet
        FileInputStream fis = null;
        Workbook workbook = null;
        Sheet sheet = null;

        try {
            //获取一个绝对地址的流
            fis = new FileInputStream(excelPath);
        }catch (Exception e){
            throw new Exception("获取文件流异常：" + e.getMessage());
        }
        //得道工作簿
        try {
            //2003版本的excel，用 .xls 结尾
            workbook = new HSSFWorkbook(fis);
        }catch (Exception e){
            try {
                workbook = new XSSFWorkbook(fis);
            }catch (IOException ee){
                throw new Exception("获取excel工作簿异常：" + e.getMessage());
            }
        }
        sheet = workbook.getSheetAt(sheetIndex);

        //定义图片存储
        Map<String, PictureData> mapList = null;
        if (excelPath.endsWith(".xls")){
            mapList = getPicturesIndex((HSSFSheet) sheet);
        }else if (excelPath.endsWith(".xlsx")){
            mapList = getPicturesIndex((XSSFSheet) sheet);
        }
        //下载图片到临时目录
        try {
            downloadPic(mapList);
        }catch (IOException e){
            throw new Exception("图片下载异常：" + e.getMessage());
        }

        //解析工作表数据
        Map<String,List<String>> data = new HashMap<String, List<String>>();
        //获得表头列的个数
        int totalColNum = sheet.getRow(headerRow).getLastCellNum();
        //判断表头个数是否匹配
        if (totalColNum != colNum){
            throw new Exception("表头数量与期望的不一致，期望表头个数：" + colNum + "，实际表头个数：" + totalColNum);
        }
        //获得数据总行数
        int totalRowNum = sheet.getLastRowNum() + 1;
        //获取所有数据
        for (int i = startRow ; i < totalRowNum ; i++){
            List<String> rowData = new ArrayList<String>();
            Row row = sheet.getRow(i);
            for (int j = startCol ; j < totalColNum ; j++){
                String picKey = i + "_" + j + ".jpg";
                if (mapList.get(picKey) != null){
                    rowData.add(tmpDir + picKey);
                }else {
                    rowData.add(row.getCell(j).getStringCellValue().toString());
                }
            }
            data.put("row_" + i, rowData);
        }
        return data;
    }

    /**
     * 获取图片和位置（xls）
     * @param sheet sheet页
     * @return 图片坐标、图片
     */
    private Map<String, PictureData> getPicturesIndex(HSSFSheet sheet){
        Map<String, PictureData> map = new HashMap<String, PictureData>();
        List<HSSFShape> list = sheet.getDrawingPatriarch().getChildren();
        for (HSSFShape shape : list){
            if (shape instanceof HSSFPicture){
                HSSFPicture picture = (HSSFPicture) shape;
                HSSFClientAnchor cAnchor = (HSSFClientAnchor) picture.getAnchor();
                PictureData pdata = picture.getPictureData();
                //行号-列号
                String key = cAnchor.getRow1() + "_" + cAnchor.getCol1() + ".jpg";
                map.put(key, pdata);
            }
        }
        return map;
    }

    /**
     * 获取图片和位置（xlsx）
     * @param sheet sheet页
     * @return 图片坐标、图片
     */
    private Map<String, PictureData> getPicturesIndex(XSSFSheet sheet){
        Map<String, PictureData> map = new HashMap<String, PictureData>();
        List<POIXMLDocumentPart> list = sheet.getRelations();
        for (POIXMLDocumentPart part : list){
            if (part instanceof XSSFDrawing){
                XSSFDrawing drawing = (XSSFDrawing) part;
                List<XSSFShape> shapes = drawing.getShapes();
                for (XSSFShape shape : shapes){
                    XSSFPicture picture = (XSSFPicture) shape;
                    XSSFClientAnchor anchor = picture.getPreferredSize();
                    CTMarker marker = anchor.getFrom();
                    String key = marker.getRow() + "-" + marker.getCol() + ".jpg";
                    map.put(key, picture.getPictureData());
                }
            }
        }
        return map;
    }

    /**
     * 下载图片至临时目录
     * @param picList   图片列表
     * @throws IOException 文件输出异常
     */
    private void downloadPic(Map<String, PictureData> picList) throws IOException {
        Object key[] = picList.keySet().toArray();
        FileOutputStream fout = null;
        for (int i = 0 ; i < picList.size() ; i++){
            PictureData pic = picList.get(key[i]);
            String picName = key[i].toString();
            String ext = pic.suggestFileExtension();
            byte[] data = pic.getData();
            fout = new FileOutputStream(tmpDir + picName);
            fout.write(data);
            fout.flush();
            fout.close();
        }
    }

    public static void main(String[] args) {
        ReadExcelClass excelUtil = new ReadExcelClass("/Users/cc/Tmp/excel_file/faceOpenDoorLog.xls",0,0,10,1,0,"/Users/cc/Tmp/excel_file");
        Map<String,List<String>> data = null;
        try {
            data = excelUtil.getDataFormExcel();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (data != null){
            for (Map.Entry<String,List<String>> row : data.entrySet()){
                System.out.println("行号：" + row.getKey());
                for (String str : row.getValue()){
                    System.out.print(str + "    ");
                }
                System.out.println();
            }
        }
    }
}
