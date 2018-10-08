package ${ @tpl_controller.packageName};

<#include "global.ftl">

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import ${@tpl_entity.packageName}.${className};
import ${@tpl_service.packageName}.${className}Service;
import ${page};

/** ${copyright}
 *
 * ${className}Controller
 * Description: ${table.remark!}
 *
 * @version : v1.0
 * @author : ${author!}
 * @since : ${now!}
 */

@Controller
@RequestMapping
public class ${className}Controller  {

    @Autowired
    private ${className}Service ${className?uncap_first}Service;


    @RequestMapping(value = "/${className?uncap_first}/list", method = RequestMethod.GET)
    public String toIndex(HttpServletRequest request, HttpServletResponse response) {
        return "${className?uncap_first}/${className?uncap_first}List";
    }

<#if table.pkCount == 1>
    @RequestMapping(value = "/${className?uncap_first}/info", method = RequestMethod.GET)
    public String toInfo(HttpServletRequest request, HttpServletResponse response,<@pkTypeAndField/>,Model model) {
        ${className} ${className?uncap_first} = ${className?uncap_first}Service.selectByPrimaryKey(<@pkField/>);
        model.addAttribute("${className?uncap_first}", ${className?uncap_first});
        return "${className?uncap_first}/${className?uncap_first}Info";
    }
</#if>


    @RequestMapping(value = "/${className?uncap_first}/add", method = RequestMethod.GET)
    public String toAdd(HttpServletRequest request, HttpServletResponse response) {
            return "${className?uncap_first}/${className?uncap_first}Add";
    }

    @RequestMapping(value = "/${className?uncap_first}/edit")
    public String toEdit(HttpServletRequest request, HttpServletResponse response,@RequestParam <@pkTypeAndField/>,Model model) {
        ${className} ${className?uncap_first} = ${className?uncap_first}Service.selectByPrimaryKey(<@pkField/>);
        model.addAttribute("<@pkField/>", <@pkField/>);
        model.addAttribute("${className?uncap_first}",${className?uncap_first});
        return "${className?uncap_first}/${className?uncap_first}Edit";
    }

    /**
     * ajax返回json分页数据，request中传递参数page,pageSize
     */
    @RequestMapping(value = "/${className?uncap_first}/json/list")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response,${className} ${className?uncap_first} ) {
        Page page = ${className?uncap_first}Service.select(${className?uncap_first}, new Page(request,response) );
        return page.toJSON();
    }


    @RequestMapping(value = "/${className?uncap_first}/save")
    @ResponseBody
    public Map save(${className} ${className?uncap_first} ) {
        ${className?uncap_first}Service.save(${className?uncap_first} );

        Map map = new HashMap();
        map.put("success",true);
        map.put("message","保存成功");
        return map;
    }

<#if table.pkCount == 1>
    @RequestMapping(value = "/${className?uncap_first}/update")
    @ResponseBody
    public Map update(${className}  ${className?uncap_first} ) {

        ${className?uncap_first}Service.updateByPrimaryKey( ${className?uncap_first} );

        Map map = new HashMap();
        map.put("success",true);
        map.put("message","保存成功");
        return map;
    }

    @RequestMapping(value = "/${className?uncap_first}/delete")
    @ResponseBody
    public Map delete(@RequestParam <@pkTypeAndField/>) {
        ${className?uncap_first}Service.deleteByPrimaryKey( <@pkField/> );
        Map map = new HashMap();

        map.put("success",true);
        map.put("message","保存成功");
        return map;
    }

</#if>
}
