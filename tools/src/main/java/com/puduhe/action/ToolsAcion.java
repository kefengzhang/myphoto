package com.puduhe.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.puduhe.util.SecurityFactory;
import com.puduhe.util.io.FileUtil;

/**
 * @author zhangkefeng
 *
 */
@Controller
public class ToolsAcion {
    
    /**
     * css格式化
     * @param request
     * @param response
     * @param model
     * @param value
     * @param character
     * @param otherCharacter
     * @return
     */
    @RequestMapping("/tools/sha1")
    public String sha1(HttpServletRequest request, 
            HttpServletResponse response,
            Model model,
            @RequestParam(value="value", required=false) String value,
            @RequestParam(value="character", required=false,defaultValue="UTF-8") String character,
            @RequestParam(value="other_character", required=false ,defaultValue="") String otherCharacter) {
        if(value==null){
            model.addAttribute("character", character);
            model.addAttribute("otherCharacter", otherCharacter);
            return "tools/sha1";
        }
        String charset = null;
        if(StringUtils.isNotBlank(otherCharacter)){
            try {
                Charset.forName(otherCharacter);
                charset = otherCharacter;
            }
            catch (UnsupportedCharsetException e) {}            
        }else{
            charset = character;
        }
        String outValue=null;
        if(StringUtils.isNotBlank(value)){
            outValue = SecurityFactory.SHA1Encode(value, charset);
        }
        model.addAttribute("outValue", outValue);
        model.addAttribute("value", value);
        model.addAttribute("character", character);
        model.addAttribute("otherCharacter", otherCharacter);
        return "tools/sha1";
    }
    /**
     * css格式化
     * @param request
     * @param response
     * @param model
     * @param value
     * @param character
     * @param otherCharacter
     * @return
     */
    @RequestMapping("/tools/css")
    public String css(HttpServletRequest request, 
            HttpServletResponse response,
            Model model,
            @RequestParam(value="value", required=false) String value) {
        if(value==null){
            return "tools/js";
        }
        String outValue=null;
        if(StringUtils.isNotBlank(value)){
        }
        model.addAttribute("outValue", outValue);
        model.addAttribute("value", value);
        return "tools/js";
    }
    /**
     * js格式化
     * @param request
     * @param response
     * @param model
     * @param value
     * @param character
     * @param otherCharacter
     * @return
     */
    @RequestMapping("/tools/js")
    public String js(HttpServletRequest request, 
            HttpServletResponse response,
            Model model,
            @RequestParam(value="value", required=false) String value) {
        if(value==null){
            return "tools/js";
        }
        String outValue=null;
        if(StringUtils.isNotBlank(value)){
        }
        model.addAttribute("outValue", outValue);
        model.addAttribute("value", value);
        return "tools/js";
    }
    /**
     * json格式化
     * @param request
     * @param response
     * @param model
     * @param value
     * @param character
     * @param otherCharacter
     * @return
     */
    @RequestMapping("/tools/json")
    public String json(HttpServletRequest request, 
            HttpServletResponse response,
            Model model,
            @RequestParam(value="value", required=false) String value) {
        if(value==null){
            return "tools/json";
        }
        String outValue=null;
        if(StringUtils.isNotBlank(value)){            
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(value);
            try{
                outValue = gson.toJson(je);
            }catch (Exception e) {}
            int l=value.length();
            if(l>5000){
                model.addAttribute("no_show_css", true);
            }            
        }
        model.addAttribute("outValue", outValue);
        model.addAttribute("value", value);
        return "tools/json";
    }
    /**
     * crc计算
     * @param request
     * @param response
     * @param model
     * @param value
     * @param character
     * @param otherCharacter
     * @return
     */
    @RequestMapping("/tools/crc")
    public String crc(HttpServletRequest request, 
            HttpServletResponse response,
            Model model,
            @RequestParam(value="value", required=false) String value,
            @RequestParam(value="character", required=false,defaultValue="UTF-8") String character,
            @RequestParam(value="other_character", required=false ,defaultValue="") String otherCharacter) {
        if(value==null){
            model.addAttribute("character", character);
            model.addAttribute("otherCharacter", otherCharacter);
            return "tools/crc";
        }
        String charset = null;
        if(StringUtils.isNotBlank(otherCharacter)){
            try {
                Charset.forName(otherCharacter);
                charset = otherCharacter;
            }
            catch (UnsupportedCharsetException e) {}            
        }else{
            charset = character;
        }
        String outValue=null;
        if(StringUtils.isNotBlank(value)){
            try {
                outValue=SecurityFactory.CRC(value, charset);
            }catch (Exception e) {}
        }
        model.addAttribute("outValue", outValue);
        model.addAttribute("value", value);
        model.addAttribute("character", character);
        model.addAttribute("otherCharacter", otherCharacter);
        return "tools/crc";
    }
    @RequestMapping("/tools/md5")
    public String md5() {
        return "tools/md5";
    }
    
    @RequestMapping("/tools/base64")
    public String base64(HttpServletRequest request, 
            HttpServletResponse response,
            Model model,
            @RequestParam(value="value", required=false) String value,
            @RequestParam(value ="value_file", required = false) MultipartFile valueFile,
            @RequestParam(value="character", required=false,defaultValue="UTF-8") String character,
            @RequestParam(value="other_character", required=false ,defaultValue="") String otherCharacter,
            @RequestParam(value="do_thing", required=false ,defaultValue="1") Integer doThing,
            @RequestParam(value="output_type", required=false ,defaultValue="1") Integer outputType,
            @RequestParam(value="max_line_chars", required=false,defaultValue="76") Integer maxLineChars,
            @RequestParam(value="out_file_name", required=false,defaultValue="base64.bin") String outFileName) {
        if(value==null){
            model.addAttribute("character", character);
            model.addAttribute("otherCharacter", otherCharacter);
            return "tools/base64";
        }
        
        String charset = null;
        if(StringUtils.isNotBlank(otherCharacter)){
            try {
                Charset.forName(otherCharacter);
                charset = otherCharacter;
            }
            catch (UnsupportedCharsetException e) {
            }            
        }else{
            charset = character;
        }
        
        
        if(StringUtils.isBlank(charset)){
            charset = "UTF-8";
        }
        String outValue=null;
        
        if(valueFile!=null&&valueFile.getSize()>0){
            byte[] bytes=FileUtil.getBytesFromFile(valueFile.getOriginalFilename());
            if(bytes!=null){
                if(doThing==1){
                    try {
                        outValue=SecurityFactory.BASE64Encode(bytes, charset);
                    }catch (Exception e) {}                    
                }else{
                    try {
                        outValue=SecurityFactory.BASE64Decode(bytes, charset);
                    }catch (Exception e) {}
                }
            }
        }else{
            if(StringUtils.isNotBlank(value)){
                if(doThing==1){
                    try {
                        outValue=SecurityFactory.BASE64Encode(value, charset);
                    }catch (Exception e) {}
                }else{
                    try {
                        outValue=SecurityFactory.BASE64Decode(value, charset);
                    }catch (Exception e) {}
                }
            }
        }
        
        if(StringUtils.isBlank(outValue)){
            return "tools/base64";
        }else{
            if(maxLineChars>0&&maxLineChars<500){
                StringBuffer sb = new StringBuffer();
                for(int i=0;i<outValue.length();i=i+maxLineChars){
                    int j = i+maxLineChars;
                    if(j>outValue.length()) j=outValue.length();
                    sb.append(outValue.substring(i, j));
                    sb.append("\n");
                }
                outValue=sb.toString();
            }
        }
        
        
        if(outputType==0){
            if(StringUtils.isBlank(outFileName)){
                outFileName = "base64.bin";
            } 
            response.setContentType("multipart/form-data");  
            response.setHeader("Content-Disposition","attachment;filename="+outFileName);
            PrintWriter out=null;
            try {
                out = response.getWriter();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            if(out!=null){
                try {
                    out.write(outValue);
                    out.flush();
                } finally {
                    out.close();
                }
                return null;
            }
        }
        model.addAttribute("outValue", outValue);
        model.addAttribute("value", value);
        model.addAttribute("character", character);
        model.addAttribute("otherCharacter", otherCharacter);
        model.addAttribute("max_line_chars", maxLineChars);
        model.addAttribute("do_thing", doThing);
        model.addAttribute("output_type", outputType);
        model.addAttribute("out_file_name", outFileName);
        
        return "tools/base64";
    }    
}
