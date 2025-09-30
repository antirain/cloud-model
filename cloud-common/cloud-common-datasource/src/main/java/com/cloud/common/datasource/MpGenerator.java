package com.cloud.common.datasource;

/**
 * @author local
 * @date 2025/9/29 17:23
 * @description
 */
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.cloud.common.datasource.entity.BaseEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collections;


public class MpGenerator {

    /** 数据库连接 **/
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/cloud_system?useSSL=false&serverTimezone=UTC";
    private static final String USER     = "root";
    private static final String PASSWORD = "123456";

    /** 项目根路径（多模块时指向 xxx/xxx-service 层） **/
    private static final String PROJECT_ROOT = System.getProperty("user.dir") + "/cloud-system";

    /** 父包名 **/
    private static final String PARENT_PKG = "com.cloud.system";

    /** 要生成的表（支持数组） **/
    private static final String[] TABLES = {"sys_menu", "sys_role", "sys_role_menu" , "sys_user", "sys_user_role"};

    public static void main(String[] args) {
        FastAutoGenerator.create(JDBC_URL, USER, PASSWORD)
                // 1. 全局配置
                .globalConfig(builder -> builder
                        .author("local")                 // 作者
                        .outputDir(PROJECT_ROOT + "/src/main/java") // 输出目录
                        .disableOpenDir()              // 生成完不打开文件夹
                        .dateType(DateType.TIME_PACK)  // 使用 java.time 包
                        .commentDate("yyyy-MM-dd")     // 注释日期格式
                        .enableSpringdoc())              // 开启 Swagger 注解
                // 2. 包配置
                .packageConfig(builder -> builder
                        .parent(PARENT_PKG)            // 父包
                        .entity("entity")       // 实体类包
                        .mapper("mapper")              // Mapper 接口包
                        .service("service")            // Service 接口包
                        .serviceImpl("service.impl")   // Service 实现包
                        .controller("controller")      // Controller 包
                        .pathInfo(Collections.singletonMap(OutputFile.xml,
                                PROJECT_ROOT + "/src/main/resources/mapper"))) // XML 输出路径
                // 3. 策略配置
                .strategyConfig(builder -> builder
                        .addInclude(TABLES)            // 表名
                        .entityBuilder()
                        .enableFileOverride()
                        .enableLombok()            // Lombok
                        .enableTableFieldAnnotation() // @TableField
                        .naming(NamingStrategy.underline_to_camel)
                        .columnNaming(NamingStrategy.underline_to_camel)
                        .idType(IdType.AUTO) // 雪花ID
                        .logicDeleteColumnName("deleted") // 逻辑删除字段
                        .versionColumnName("version")     // 乐观锁字段
                        .superClass(BaseEntity.class)
                        .controllerBuilder()
                        .enableFileOverride()
                        .enableRestStyle()           // @RestController
                        .enableHyphenStyle()         // 映射连字符 /order-info
                        .serviceBuilder()
                        .enableFileOverride()
                        .formatServiceFileName("%sService")
                        .formatServiceImplFileName("%sServiceImpl")
                        .mapperBuilder()
                        .enableFileOverride()
                        .mapperAnnotation(Mapper.class)   // @Mapper
                        .formatMapperFileName("%sMapper")
                        .formatXmlFileName("%sMapper"))
                // 4. 模板引擎（默认 Velocity）
                .templateEngine(new VelocityTemplateEngine())
                .execute();
    }
}