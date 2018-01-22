package com.isfce.pidw.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;

import com.isfce.pidw.data.ICoursJpaDAO;
import com.isfce.pidw.filter.DateFilter;
import com.isfce.pidw.model.Module;



@Controller
@RequestMapping("/test")
public class MasterControl {
	
	
	
	public class UserPreferenceService {
	    private String dateFormat;

	    public UserPreferenceService() {
	        this.dateFormat = "yyyy-MM-dd" ;
	    }
		public String getDateFormat() {		    return dateFormat;		}
	    
	}
	
	private UserPreferenceService userPreferenceService = new UserPreferenceService();
	


	public MasterControl() {	}
	
	
	
	
	
	@RequestMapping(value = "/json")
	public String testJson(Model model) {
	    System.out.printf( "[MasterControl]"  +  "[testJson]"  +  "[]" );

	    return "test/json";
	}
	

	
	
	@RequestMapping(value = "/date")
	public String testDate(Model model) {
	    System.out.printf( "[MasterControl]"  +  "[testDate]"  +  "[]" );
	    
	    model.addAttribute("filter", new DateFilter());
	    return "test/testdate";
	}

	
	
	

	
	
	@RequestMapping(value = "/date", method = RequestMethod.POST)
	public String testDatePost(@ModelAttribute("filter") DateFilter filter,  BindingResult errors, 
			@RequestParam(value = "savedId", required = false) String savedId, Model model , RedirectAttributes rModel) {
		System.out.printf( "[MasterControl]"  +  "[testDate]"  +  "[POST]" );
		
		
		System.out.printf( model.toString() );
		
		
	    System.out.printf(filter.getLoadingStartDate().toString());
	    System.out.printf(dateFormat());
	    
	    return "test/testdate";
	}

	
	
	
	@ModelAttribute("dateFormat")
	public String dateFormat() {
	    return userPreferenceService.getDateFormat();
	}

	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {
	    //The date format to parse or output your dates
	    SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormat());
	    //Create a new CustomDateEditor
	    CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
	    //Register it as custom editor for the Date type
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	

}




















/*

public interface Pair<K, V> {
    public K getKey();
    public V getValue();
}

public class OrderedPair<K, V> implements Pair<K, V> {

    private K key;
    private V value;

    public OrderedPair(K key, V value) {
	this.key = key;
	this.value = value;
    }

    public K getKey()	{ return key; }
    public V getValue() { return value; }
}


*/