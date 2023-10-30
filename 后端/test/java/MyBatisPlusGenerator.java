import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.rules.DateType;

public class MyBatisPlusGenerator {

    static final String templatePath = "template/ServiceTemplate.java.vm";

    public static void main(String[] args) {
        //TODO 指定输出目录
        String outputDir = "E:\\codes\\runok\\backend\\userService\\src\\main\\java";

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/questionnaire",
                        "root", "jm951013")
                .globalConfig(builder -> {
                    //TODO 设置作者
                    builder .outputDir(outputDir)
                            .dateType(DateType.ONLY_DATE)
                            .disableOpenDir();
                })
                .packageConfig(builder -> {
                    // 设置父包名
                    builder.parent("com.aim").moduleName("questionnaire").xml("mapper");
                })
                .strategyConfig(builder -> {
                    // 设置需要生成的表名
                    builder

                            .likeTable(new LikeTable("question_bank"))
                            // 设置过滤表前缀
                            .serviceBuilder().formatServiceFileName("%sService");
                })
                .templateConfig(builder -> {
                    builder.service(templatePath).serviceImpl(null).serviceImpl(null);
                })
                .execute();
    }

}
