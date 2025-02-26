package com.akd;

import com.akd.utils.FileUtil;
import com.akd.utils.StringUtil;

import java.util.Date;

public class CreateKdmTest {
    public static  void main(String[] ages){
        long date = new Date().getTime();
        for(int j = 0;j<100000;j++){
            String readToString = FileUtil.readToString("H:\\h_YU-JIAN-NI-ZHEN-HAO_FTR_S_CMN-QMS_100M_51_2K_20180314_HXFILM_OV_A52115_6081585.xml");
            String sub = StringUtil.zeroFill(j, 10);
            String replace = readToString.replace("urn:uuid:edc6470d-6da0-4839-ba86-2f6fab559a7a", "urn:uuid:edc6470d-6da0-4839-ba86-2f" + sub).replace("+97bi97ZLI=",  sub);
            FileUtil.writeToFile(replace, "H:\\test\\h_YU-JIAN-NI-ZHEN-HAO_FTR_S_CMN-QMS_100M_51_2K_20180314_HXFILM_OV_A521"+ sub +".xml");
            System.out.println("第" + j + "文件生成完成");
        }
        long date1 = new Date().getTime();
        System.out.println("生成完成，用时："+(date1-date));
    }
}
