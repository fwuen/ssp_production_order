package converter;

import data.db.ProductionProvider;
import data.model.Production;

import javax.enterprise.inject.spi.CDI;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

@Named
@FacesConverter(forClass = Production.class)
public class ProductionConverter implements Converter {
    ProductionProvider productionProvider = CDI.current().select(ProductionProvider.class).get();

    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        if (modelValue == null) {
            return "";
        }

        if (modelValue instanceof Production) {
            return String.valueOf(((Production) modelValue).getPrId());
        } else {
            throw new ConverterException(new FacesMessage(modelValue + " is not a valid Product"));
        }
    }

    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        if (submittedValue == null || submittedValue.isEmpty()) {
            return null;
        }

        try {
            return productionProvider.findProductionById(Integer.valueOf(submittedValue));
        } catch (NumberFormatException e) {
            throw new ConverterException(new FacesMessage(submittedValue + " is not a valid Production ID"), e);
        }
    }
}
