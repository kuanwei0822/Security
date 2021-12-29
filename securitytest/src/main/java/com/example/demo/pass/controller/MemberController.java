package com.example.demo.pass.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.pass.model.Member;
import com.example.demo.pass.model.MemberService;

@RestController
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	// 測試網頁1
	@RequestMapping(path="/test1",method = RequestMethod.GET)
	public String testWeb1() {
		return "hello1";
	}
	// 測試網頁2
	@RequestMapping(path="/test2",method = RequestMethod.GET)
	public String testWeb2() {
		return "hello2";
	}
	// 測試網頁3
	@RequestMapping(path="/test3",method = RequestMethod.GET)
	public String testWeb3() {
		return "hello3";
	}
	
	// 測試網頁4
	@RequestMapping(path="/test4",method = RequestMethod.GET)
	public String testWeb4() {
		return "hello4";
	}
	
	
	// 登入頁面(這頁沒鎖)
//	@RequestMapping(path="/loginPage",method = RequestMethod.GET)
//	public ModelAndView loginPage() {
//		return new ModelAndView("loginPage.html");
//	}
	
	
	// 登入成功頁面(這頁沒鎖)
	@RequestMapping(path="/loginSuccessPage",method = RequestMethod.POST)
	public String loginSuccessPage() {
		return "welcom";
	}
	
	
	
		
	// 查詢第一筆資料(這頁有鎖)
	@RequestMapping(path="/abc",method = RequestMethod.GET)
	public String getOneData() {
		
		Optional<Member> oneMember = memberService.getOneMember();
		Member member = oneMember.get();
		
		return member.getName() + " || " + member.getPaasword();
		
	}
	
	// 查詢資料ByName(這頁沒鎖)
	@RequestMapping(path="/def",method = RequestMethod.GET)
	public String getOneMemberByName() {
		
		Member oneMemberByName = memberService.getOneMemberByName();
		
		return oneMemberByName.getName() + " || " + oneMemberByName.getPaasword();
		
	}
	
	
	
}



//@RestController
//@EnableSwagger2
////@SecurityRequirement(name = "javainuseapi")為類別設定身分
//@Api(value = "會員類型資料", tags = "會員類型資料")
//@RequestMapping("/api/v1/member")
//
//public class MemberController {
//
////	// 定義日誌紀錄器物件
////	public static final Logger logger = LogManager.getLogger(MemberController.class);
//
////	@Autowired
////	Swagger2Config swagger2Config;
//	
//	@Autowired
//	Member member;
//
//	@Autowired
//	MemberService memberservice;
//	
////	@Autowired
////	ApiStatusUtils apiStatusUtils;
//
////	@Autowired
////	JwtTokenUtil jwt;
//	
////	@Autowired
////	MemberRepository mr;
//
//	@GetMapping("/list")
//	@ApiOperation(value = "回傳獲取全部的資料",response = Member.class, produces = "application/json",authorizations = { @Authorization(value="authkey") })
////	@Authorization(value = "",)
//	//public Map<String, Object> list() {
//	public void list() {
//		List<Member> member = memberservice.getAllMember();
//		//Map<String, Object> testmap = apiStatusUtils.apitask("搜尋成功", member, 200);
//		
//		
//		//return testmap;
//
//	}

//	@PutMapping("/{memId}")
//	@ApiOperation(value = "更新會員資料", response = Member.class, produces = "application/json")
//	public String update(Long memId, Member member) {
//
//		member.setMemId(memId);
//		memberservice.savemember(member);
//
//		return "更新成功";
//	}
//
//	@GetMapping("/{memId}")
//	@ApiOperation(value = "獲取單一會員", response = Member.class, produces = "application/json")
//	public Member getMemberById(@PathVariable("memId") long memId) {
//
//		return memberservice.getMemberById(memId);
//
//	}
//
//	@PostMapping("/login")
//	public Collection<GrantedAuthority> login(@PathVariable("memEmail") String memEmail,String memPassword) {
//	
//	return mr.loadUserAuthorities(memEmail);
//	
//	}
	//----------------------------------------
//	@PostMapping("/login")
//	@ApiOperation(value = "會員登入", response = Member.class, produces = "application/json")
//	public String login(@PathVariable("memEmail") String memEmail, @PathVariable("memPassword") String memPassword) {
//
//		String MemEmail = member.getMemEmail();
//		String MemPassword = member.getMemPassword();
//		List<Member> mem = memberservice.findByMemEmailAndMemPassword(MemEmail, MemPassword);
//		if (mem != null) {
//			String token = jwt.tokensign(MemEmail, MemPassword);
//			return token;
//		} else {
//
//			return "有問題啦";
//
//			// 取得會員信箱跟密碼實體，使用SQL去比對帳密是否存在，存在後回傳並叫用TOKEN出來
//
//		}
//	}
//}