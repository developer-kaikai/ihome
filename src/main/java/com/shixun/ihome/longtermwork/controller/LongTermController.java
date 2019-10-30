package com.shixun.ihome.longtermwork.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shixun.ihome.longtermwork.service.LongTermService;
import com.shixun.ihome.publicservice.pojo.IOrder;
import com.shixun.ihome.publicservice.pojo.IOrderLong;
import com.shixun.ihome.publicservice.pojo.IStaff;
import com.shixun.ihome.publicservice.pojo.IUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@Api(description = "长期工订单模块")
@RequestMapping("json/longTermOrder")
public class LongTermController {
    @Autowired
    private LongTermService longTermService;

    @ApiOperation(value = "添加长期工订单")
    @ApiImplicitParam(name = "order",value="订单实体类",required = true,dataType = "IOrder")
    @PostMapping("/addOrder")
    @ResponseBody
    public Boolean addOrder(@RequestBody IOrder order){
        IOrderLong orderLong=new  IOrderLong();
        boolean result=longTermService.addOrder(order);
        return  result;
    }
    @ApiOperation(value = "取消维修订单")
    @RequestMapping(value = "/cancelOrder",method = RequestMethod.POST)
    @ResponseBody
    public Boolean cancelOrder(int id){
        boolean result=longTermService.cancelOrder(id);
        return result;
    }
    @ApiOperation(value = "确认维修订单")
    @RequestMapping(value = "/confirmOrder",method = RequestMethod.POST)
    @ResponseBody
    public Boolean confirmOrder(int id){
        boolean result=longTermService.confirmOrder(id);
        return result;
    }

    @ApiOperation(value = "文件上传")
    @PostMapping(value = "/fileUpload")
    @ResponseBody
    public boolean fileUpload(@RequestParam(value = "file") MultipartFile file, Model model, HttpServletRequest request) throws ParseException {
//        String name=request.getParameter("name");
//        String address=request.getParameter("address");
//        String time=request.getParameter("time");
//        String phone=request.getParameter("phone");
//        String type=request.getParameter("type");
//        String website=request.getParameter("website");
//        String introduction=request.getParameter("introduction");
//        String scale=request.getParameter("scale");
//        String email=request.getParameter("email");

//        System.out.println(type+time+introduction);
        //boolean result=false;
        if (file.isEmpty()) {
            System.out.println("文件为空");
        }
//        System.out.println(email);
        String path1=request.getServletContext().getRealPath("staffInfo");
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        fileName = UUID.randomUUID() + suffixName; // 新文件名
//        Enterprise enterprise=new Enterprise();
//        enterprise.setAddress(address);
//        enterprise.setIntroduction(introduction);
//        enterprise.setName(name);
//        enterprise.setPath("/userImages"+fileName);
//        enterprise.setPhone(phone);
//        enterprise.setScale(scale);
//        enterprise.setState(1);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//        Date date=sdf.parse(time);
//        enterprise.setTime(date);
//        enterprise.setWebsite(website);
//        enterprise.setType(type);

//        if(enterpriseService.insert(enterprise)==false){
//            return "fail";
//        }
        System.out.println("---------------------------"+path1);
        System.out.println(path1);
        File dest = new File(path1 + fileName);
        //int Adminid=userService.findByAdminEmail(email);
        //Admin admin=userService.selectAdmin(Adminid);
        //int enterpriseId=enterpriseService.selectByName(name).getId();
        //Enterprise enterprise1=enterpriseService.select(enterpriseId);
       // admin.setEnterpriseid(enterprise1.getId());
       // userService.updateAdmin(admin);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return  false;
        }
    }
    @ApiOperation(value = "文件下载")
    @PostMapping("/download")
    @ResponseBody
    public void download(HttpServletResponse response) throws UnsupportedEncodingException {

        //String path=examineService.select1(examineId).getPath();

        String path="C:\\Users\\蔡海瀚\\AppData\\Local\\Temp\\tomcat-docbase.15988616472090539968.8080\\staffInfoc5004266-a8e4-4384-b90a-e9c64c07de0a.jpg";
        String name="管理人员用例图";
        File file = new File(path);
        String suffixName = path.substring(path.lastIndexOf("."));
        String filename=name+suffixName;
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(filename.getBytes("UTF-8"), "iso-8859-1"));

            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            //System.out.println("----------file download" + filename);
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                bis.close();
                fis.close();

                System.out.println("----------file download" + filename);
            } catch (IOException e) {

                e.printStackTrace();

            }

        }


    }
//    @ApiOperation(value = "查看空闲长期工")
//    @RequestMapping(value = "/selectLongTerm",method = RequestMethod.POST)
//    @ResponseBody
//    public List<IStaff> selectLongTermStaffs(){
//        //List<IStaff> iStaffList=longTermService.selectLongTermStaffs();
//        return iStaffList;
//    }
    @ApiOperation(value = "测试")
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    @ResponseBody
    public void test(@RequestBody JSONObject getcode){

//        String code=getcode.getString("code");
//        JSONObject userInfo=getcode.getJSONObject("userInfo");
//        int id=userInfo.getInteger("id");
//        JSONArray name=userInfo.getJSONArray("name");
//        List<Integer> list1=(List)name;
//        System.out.println(code);
//        System.out.println(userInfo);
//        System.out.println(id);
//        for (Integer id1:list1) {
//            System.out.println(id1);
//        }
//        System.out.println(name);
        IUser iUser=new IUser();
        iUser.setWeixinId(1);
        //iUser.setWeixin();
        System.out.println(iUser.getPhone());
//        if(iUser.getPhone().equals("")){
//        if("".equals(iUser.getPhone())){
//            System.out.println(1);
//        }else {
//            System.out.println(0);
//        }
        if(iUser.getPhone()== null){
            System.out.println(1);
        }else {
            System.out.println(0);
        }
    }

}
