package tk.mybatis.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class Generator {
    public static void main(String[] args) {
        ArrayList<String> warnings = new ArrayList<>();

        boolean overwrite = false;

        InputStream inputStream = Generator.class.getResourceAsStream("/generatorConfig.xml");

        ConfigurationParser configurationParser = new ConfigurationParser(warnings);
        try {
            Configuration configuration = configurationParser.parseConfiguration(inputStream);
            assert inputStream != null;
            inputStream.close();
            DefaultShellCallback ShellCallback = new DefaultShellCallback(overwrite);

            // 重点
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, ShellCallback, warnings);

            myBatisGenerator.generate(null);

            for (String warning : warnings) {
                System.out.println(warning);
            }
        } catch (IOException | InvalidConfigurationException | XMLParserException | SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
