package ${basePackage}.model;

import lombok.Data;

/**
 * @Author: ${author}
 * @Date: ${createTime}
 * @Description:${description}
 */
@Data
public class DataModel {

<#list modelConfig.models as modelInfo>

    <#--    有分组-->
    <#if modelInfo.groupKey??>
    /**
     * ${modelInfo.groupName}
     */
     public ${modelInfo.type} ${modelInfo.groupKey} = new ${modelInfo.type}();
    /**
     * ${modelInfo.description}
     */
     @Data
     public static class ${modelInfo.type} {
     <#list modelInfo.models as modelInfo>
         <#if modelInfo.description??>
             /**
             * ${modelInfo.description}
             */
         </#if>
         public ${modelInfo.type} ${modelInfo.fieldName}<#if modelInfo.defaultValue??> = ${modelInfo.defaultValue?c}</#if>;
     </#list>
        }
    <#else>
        <#if modelInfo.description??>
            /**
            * ${modelInfo.description}
            */
        </#if>
        public ${modelInfo.type} ${modelInfo.fieldName}<#if modelInfo.defaultValue??> = ${modelInfo.defaultValue?c}</#if>;
    </#if>
</#list>

}
