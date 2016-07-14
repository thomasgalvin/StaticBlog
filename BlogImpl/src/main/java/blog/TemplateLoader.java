package blog;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateLoader
{
    public static final String DEFAULT_TEMPLATE_NAME = "default";
    private static final Logger logger = LoggerFactory.getLogger( TemplateLoader.class );
    private File layoutsDir;
    private Configuration templateConfig;
    private Set<String> unfoundTemplates = new HashSet();
    
    public TemplateLoader( File layoutsDir ){
        this.layoutsDir = layoutsDir;
        reload();
    }
    
    public void reload(){
        loadTemplates( layoutsDir );
    }
    
    private void loadTemplates( File layoutsDir ){
        templateConfig = new Configuration( Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS );
        templateConfig.setDefaultEncoding( "UTF-8" );
        templateConfig.setTemplateExceptionHandler( TemplateExceptionHandler.HTML_DEBUG_HANDLER );
        
        layoutsDir.mkdirs();
        File defaultTemplate = new File( layoutsDir, DEFAULT_TEMPLATE_NAME + ".ftl" );
        if( !defaultTemplate.exists() ){
            try {
                InputStream input = getClass().getClassLoader().getResourceAsStream( "blog/default.ftl" );
                String template = IOUtils.toString( input );
                FileUtils.write( defaultTemplate, template );
            }
            catch( Throwable t ){
                logger.error( "Error creating default template", t );
            }
        }
        
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        File[] files = layoutsDir.listFiles();
        for( File file : files ) {
            String name = file.getName();
            if( name.endsWith( ".ftl" ) ) {
                try {
                    String template = FileUtils.readFileToString( file );
                    name = name.substring( 0, name.length() - 4 ).toLowerCase();
                    stringLoader.putTemplate( name, template );
                }
                catch( Throwable t ) {
                    logger.error( "Error loading layout: " + file.getAbsolutePath(), t );
                }
            }
        }
        
        templateConfig.setTemplateLoader( stringLoader );
    }
    
    public Template loadTemplate( String name ) throws Exception {
        Template template = null;
        
        if( !StringUtils.isBlank( name ) ){
            try {
                template = templateConfig.getTemplate( name.toLowerCase() );
            }
            catch( Throwable t ){
                if( !unfoundTemplates.contains( name ) ){
                    logger.warn( "Unable to load template '" + name + ".ftl' - using default" );
                    unfoundTemplates.add( name );
                }
            }
        }
        
        if( template == null ){
            template = templateConfig.getTemplate( DEFAULT_TEMPLATE_NAME );
        }
        
        return template;
    }
}
