package com.ikeda.mydata.controllers;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ikeda.mydata.models.MyData;
import com.ikeda.mydata.repositories.MyDataRepository;
import com.ikeda.mydata.services.MyDataService;

@Controller
public class MyDataController {
	@Autowired
	MyDataRepository repository;

	@Autowired
	private MyDataService service;

	@PostConstruct
	public void init() {
		MyData d1 = new MyData();
		d1.setName("ikeda");
		d1.setAge(123);
		d1.setMail("ikeda@hoge.com");
		d1.setMemo("0000000000");
		repository.saveAndFlush(d1);

		MyData d2 = new MyData();
		d2.setName("masa");
		d2.setAge(12);
		d2.setMail("masa@fuga.com");
		d2.setMemo("0000000000");
		repository.saveAndFlush(d2);

		MyData d3 = new MyData();
		d3.setName("ik");
		d3.setAge(23);
		d3.setMail("ik@foo.com");
		d3.setMemo("0000000000");
		repository.saveAndFlush(d3);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(@ModelAttribute("formModel") MyData mydata, ModelAndView mav) {
		mav.setViewName("index");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "MyDataのサンプルです");
		Iterable<MyData> list = service.getAll();
		mav.addObject("datalist", list);

		return mav;
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public ModelAndView find(ModelAndView mav) {
		mav.setViewName("find");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "MyDataのサンプルです");
		mav.addObject("value", "");
		List<MyData> list = service.getAll();
		mav.addObject("datalist", list);
		
		return mav;
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public ModelAndView search(HttpServletRequest request, ModelAndView mav) {
		String param = request.getParameter("fstr");
		if(param == "") {
			mav = new ModelAndView("redirect:/find");
		} else {
			mav.addObject("title", "Find result");
			mav.addObject("msg", "「"+ param + "」の検索結果" );
			mav.addObject("value", param);
			List<MyData> list = service.find(param);
			mav.addObject("datalist", list);
		}
		
		return mav;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@ModelAttribute MyData mydata, @PathVariable int id, ModelAndView mav) {
		mav.setViewName("edit");
		mav.addObject("title", "edit mydata.");
		Optional<MyData> data = repository.findById((long) id);
		mav.addObject("formModel", data.get());

		return mav;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable int id, ModelAndView mav) {
		mav.setViewName("delete");
		mav.addObject("title", "delete mydata.");
		Optional<MyData> data = repository.findById((long) id);
		mav.addObject("formModel", data.get());

		return mav;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView form(@ModelAttribute("formModel") @Validated MyData mydata, BindingResult result,
			ModelAndView mav) {
		ModelAndView res = null;

		if (!result.hasErrors()) {
			repository.saveAndFlush(mydata);
			res = new ModelAndView("redirect:/");
		} else {
			mav.setViewName("index");
			mav.addObject("msg", "sorry, error is occurd...");
			Iterable<MyData> list = repository.findAll();
			mav.addObject("datalist", list);
			res = mav;
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView update(@ModelAttribute MyData mydata, ModelAndView mav) {
		repository.saveAndFlush(mydata);
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView remove(@RequestParam long id, ModelAndView mav) {
		repository.deleteById(id);

		return new ModelAndView("redirect:/");
	}
}
