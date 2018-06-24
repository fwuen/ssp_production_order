package bean;

import lombok.Getter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@ManagedBean
@SessionScoped
public class UserSessionBean {
    private static final long serialVersionUID = 1523479642013931903L;

    @Getter
    private String currentLocale = Locale.GERMAN.toString();

    private static Map<String, Locale> availableLocales;
    static
    {
        availableLocales = new LinkedHashMap<>();
        availableLocales.put("English", Locale.ENGLISH);
        availableLocales.put("Deutsch", Locale.GERMAN);
    }

    public void setCurrentLocale(String newLocale)
    {
        this.currentLocale = newLocale;
        for (Map.Entry<String, Locale> entry : availableLocales.entrySet())
        {
            if (entry.getValue().toString().equals(newLocale))
                FacesContext.getCurrentInstance().getViewRoot().setLocale(entry.getValue());
        }
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getViewRoot().getViewId().substring(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Locale> getAvailableLocales()
    {
        return availableLocales;
    }
}