package kg.gov.mf.loan.web.config;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import kg.gov.mf.loan.admin.sys.model.MessageResource;
import kg.gov.mf.loan.admin.sys.service.MessageResourceService;

public class DatabaseDrivenMessageSource extends AbstractMessageSource implements ResourceLoaderAware {
    
    private ResourceLoader resourceLoader = new DefaultResourceLoader();
 
    private final Map properties = new HashMap<>();
 
    @Autowired
    private MessageResourceService messageResourceService;
 
    public DatabaseDrivenMessageSource() {
        reload();
    }
 
    public DatabaseDrivenMessageSource(MessageResourceService messageResourceService) {
        this.messageResourceService = messageResourceService;
        reload();
    }
    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String msg = getText(code, locale);
        MessageFormat result = createMessageFormat(msg, locale);
        return result;
    }
 
    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        return getText(code, locale);
    }
 
    private String getText(String code, Locale locale) {
        Map<String, String> localized = (Map<String, String>) properties.get(code);
        String textForCurrentLanguage = null;
        if (localized != null) {
            textForCurrentLanguage = localized.get(locale.getLanguage());
            if (textForCurrentLanguage == null) {
                textForCurrentLanguage = localized.get(Locale.ENGLISH.getLanguage());
            }
        }
        if (textForCurrentLanguage==null) {
            logger.debug("Fallback to properties message");
            try {
                textForCurrentLanguage = getParentMessageSource().getMessage(code, null, locale);
            } catch (Exception e) {
                logger.error("Cannot find message with code: " + code);
            }
        }
        return textForCurrentLanguage != null ? textForCurrentLanguage : code;
    }
 
    public void reload() {
        properties.clear();
        properties.putAll(loadTexts());
    }
 
    protected Map<String, Map<String, String>> loadTexts() {
        
        Map<String, Map<String, String>> m = new HashMap<>();
        List<MessageResource> texts = messageResourceService.findAll();
        for (MessageResource text : texts) {
            Map<String, String> v = new HashMap<>();
            v.put("en", text.getEng());
            v.put("ru", text.getRus());
            v.put("kg", text.getKgz());
            m.put(text.getMessageKey(), v);
        }
        return m;
    }
 
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = (ResourceLoader)(resourceLoader != null?resourceLoader:new DefaultResourceLoader());
    }
 
}
