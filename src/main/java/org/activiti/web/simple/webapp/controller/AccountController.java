package org.activiti.web.simple.webapp.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.identity.Group;
import org.activiti.web.simple.webapp.model.User;
import org.activiti.web.simple.webapp.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Resource(name="accountServiceImpl")
	private AccountService accountService;
	
	ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return null;
	}
	
	/**
	 * ��ת����¼ҳ��
	 * @return
	 */
	@RequestMapping(value="/login",method={RequestMethod.POST,RequestMethod.GET})
	public String login(){
		return "login";
	}
	
	/**
	 * ִ���û���¼
	 * @param username���ܱ��ύ�������û���
	 * @param password���ܱ��ύ����������
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/loginin",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView loginin(@RequestParam("username")String username,@RequestParam("password")String password,HttpServletRequest request, HttpServletResponse response){
		ModelAndView view=new ModelAndView();
		String forword="";
		String message="";
		if((username!=null&&username.length()>0)&&(password!=null&&password.length()>0)){
			boolean b = accountService.checkPassword(username, password);
			forword=b?"main":"login";
			if(b){
				User user=new User();
				user.setId(username);
				user.setPassword(password);
				request.getSession().setAttribute("loginuser", user);
			}else{
				message="�û������������!";
			}
		}else{
			forword="login";
		}
		view.setViewName(forword);
		view.addObject("message", message);
		return view;
	}
	
	
	
	/**
	 * ��ת���û�����ҳ��
	 * @return
	 */
	@RequestMapping(value="/userwork",method={RequestMethod.POST,RequestMethod.GET})
	public String userwork(){
		return "user/userwork";
	}
	
	
	
	/**
	 * ��ת���û�����ҳ��
	 * @return
	 */
	@RequestMapping(value="/groupwork",method={RequestMethod.POST,RequestMethod.GET})
	public String groupwork(){
		return "group/groupwork";
	}
	
	/**
	 * �鿴�û��б�
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/userlist",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView userlist(HttpServletRequest request, HttpServletResponse response){
		List<org.activiti.engine.identity.User> listuser = accountService.createUserQuery().list();
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("user/listuser");
		modelAndView.addObject("listuser", listuser);
		return modelAndView;
	}
	
	
	/**
	 * �鿴��Ա�б�
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/grouplist",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView grouplist(HttpServletRequest request, HttpServletResponse response){
		List<Group> listgroup = accountService.createGroupQuery().list();
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("group/listgroup");
		modelAndView.addObject("listgroup", listgroup);
		return modelAndView;
	}
	
}
