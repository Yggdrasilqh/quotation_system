package com.yggdrasil.controller;

import com.yggdrasil.entity.Scheme;
import com.yggdrasil.repository.PlantRepository;
import com.yggdrasil.repository.PlantTypeRepository;
import com.yggdrasil.repository.SchemeRepository;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chenq on 2017/4/4,004.
 */
@RestController

public class downloadController {
    @Resource
    private SchemeRepository schemeRepository;
    @Resource
    private PlantRepository plantRepository;
    @Resource
    private PlantTypeRepository typeRepository;

    @RequestMapping("/getExcel")
    public void getExcel(int schemeID, String schemeName, int[] writ, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletRequest.setCharacterEncoding("UTF-8");
        List<Scheme> schemes = schemeRepository.findBySchemeIDOrderByRow(schemeID);
        String[] excelHead = {"位置一", "位置二", "植物名称", "植物图片", "数量", "型号", "单价", "总价", "备注", "图片备注", "成本单价", "成本总价"};
        int colNum;
        int rowNum = schemes.size() + 2;
        List<Integer> index = new ArrayList<>();
        if (null != writ) {
            colNum = Scheme.class.getDeclaredFields().length;
            for (int w : writ) {
                index.add(w);
            }
        } else {
            colNum = 12;
            for (int i = 0; i < 12; i++) {
                index.add(i);
            }
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFCellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(HSSFCellStyle.THIN_BACKWARD_DIAG);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//   style.setLeftBorderColor(HSSFColor.RED.index);

        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框


        HSSFFont font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 18);//设置字体大小


        HSSFFont font1 = wb.createFont();
        font1.setFontName("仿宋_GB2312");
        font1.setFontHeightInPoints((short) 16);//设置字体大小

        HSSFFont font2 = wb.createFont();
        font2.setFontName("仿宋_GB2312");
        font2.setFontHeightInPoints((short) 14);//设置字体大小

        style.setWrapText(true);

        Sheet sheet = wb.createSheet("sheet " + ((int) (100000 * Math.random())));

        // 设置列的宽度
        sheet.setDefaultColumnWidth(30);
        sheet.setDefaultRowHeight((short) 400);
        Row row;
        Cell cell;
        for (int r = 0; r < rowNum; r++) {
            row = sheet.createRow(r);
            if (r == 0) {
                sheet.addMergedRegion(new CellRangeAddress(r, r, 0, colNum - 1));

                row.setHeightInPoints(30);
            }

            for (int c = 0; c < colNum; c++) {
                if (index.get(c) % 3 != 0) {
                    sheet.setColumnWidth(index.get(c), 4020);
                }

                cell = row.createCell(c);
                cell.setCellStyle(style);
                if (r == 0) {
                    style.setFont(font);
                    cell.setCellValue(schemeName);
                    c = colNum - 1;
                } else {
                    if (r == 1) {
                        style.setFont(font1);
                        cell.setCellValue(excelHead[index.get(c)]);
                    } else {
                        style.setFont(font2);
                        switch (index.get(c)) {
                            case 0:
                                cell.setCellValue(schemes.get(r - 2).getPosition1());
                                break;
                            case 1:
                                cell.setCellValue(schemes.get(r - 2).getPosition2());
                                break;
                            case 2:
                                cell.setCellValue(plantRepository.findOne(schemes.get(r - 2).getPlantID()).getName());
                                break;
                            case 3:
//                                sheet.auto
                                byte[] pic = plantRepository.findOneOnlyImage(schemes.get(r - 2).getPlantID());
                                ByteArrayInputStream in = new ByteArrayInputStream(pic);
                                BufferedImage image = ImageIO.read(in);
//                                System.out.println(image.getWidth() + " " + image.getHeight());
                                sheet.getRow(r).setHeightInPoints(image.getHeight() * ((5 + 30 * 7) * 72 / 96) / image.getWidth());
                                System.out.println(image.getHeight() * ((5 + 30 * 7) * 72 / 96) / image.getWidth());
                                double scale;
                                if ((double) image.getWidth() / image.getHeight() < 1.777777778) {
                                    scale = ((double) 1080 / image.getHeight()) * 0.08;
                                } else
                                    scale = ((double) 1920 / image.getWidth()) * 0.08;
                                sheet.addMergedRegion(new CellRangeAddress(r, r, c, c));
                                HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();
                                HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) c, r, (short) (c + 1), r + 1);
                                patriarch.createPicture(anchor, wb.addPicture(pic, HSSFWorkbook.PICTURE_TYPE_JPEG)).resize(scale);
                                break;
                            case 4:
                                cell.setCellValue(schemes.get(r - 2).getNumber());
                                break;
                            case 5:
                                cell.setCellValue(plantRepository.findOne(schemes.get(r - 2).getPlantID()).getType());
                                break;
                            case 6:
                                cell.setCellValue(typeRepository.findByName(plantRepository.findOne(schemes.get(r - 2).getPlantID()).getType()).getPrice());
                                break;
                            case 7:
                                cell.setCellValue(schemes.get(r - 2).getNumber() * typeRepository.findByName(plantRepository.findOne(schemes.get(r - 2).getPlantID()).getType()).getPrice());
                                break;
                            case 8:
                                cell.setCellValue(schemes.get(r - 2).getComment());
                                break;
                            case 9:
                                try {
                                    byte[] picComment = schemes.get(r - 2).getCommentImage();
                                    ByteArrayInputStream inComment = new ByteArrayInputStream(picComment);
                                    BufferedImage CommentImage = ImageIO.read(inComment);
//                                    sheet.getRow(r).setHeightInPoints(CommentImage.getHeight() * ((5 + 30 * 7) * 72 / 96) / CommentImage.getWidth());
                                    sheet.addMergedRegion(new CellRangeAddress(r, r, c, c));
                                    System.out.println(CommentImage.getHeight());
                                    double scaleComment;
                                    if ((double) CommentImage.getWidth() / CommentImage.getHeight() < 1.777777778) {
                                        scaleComment = ((double) 1080 / CommentImage.getHeight()) * 0.08;
                                    } else
                                        scaleComment = ((double) 1920 / CommentImage.getWidth()) * 0.08;

                                    HSSFPatriarch patriarchComment = (HSSFPatriarch) sheet.createDrawingPatriarch();
                                    HSSFClientAnchor anchorComment = new HSSFClientAnchor(0, 0, 0, 0, (short) c, r, (short) (c + 1), r + 1);
                                    patriarchComment.createPicture(anchorComment, wb.addPicture(picComment, HSSFWorkbook.PICTURE_TYPE_JPEG)).resize(scaleComment);

                                } catch (Exception e) {
                                    cell.setCellValue("");
                                }
                                break;


                            case 10:
                                cell.setCellValue(plantRepository.findOne(schemes.get(r - 2).getPlantID()).getPrice());
                                break;
                            case 11:
                                cell.setCellValue(schemes.get(r - 2).getNumber() * plantRepository.findOne(schemes.get(r - 2).getPlantID()).getPrice());
                                break;
                        }
                    }
                }
            }
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        df.format(new Date());// new Date()为获取当前系统时间
        httpServletResponse.setContentType("application/octet-stream");
        httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + df.format(new Date()) + new String(schemeName.getBytes("utf-8"), "ISO8859-1") + ".xls");
        BufferedOutputStream bos = new BufferedOutputStream(httpServletResponse.getOutputStream());
        wb.write(bos);
        bos.close();
    }
}
