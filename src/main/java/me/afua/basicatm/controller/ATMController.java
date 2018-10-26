package me.afua.basicatm.controller;

import me.afua.basicatm.model.Transaction;
import me.afua.basicatm.model.TransactionRepository;
import me.afua.basicatm.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ATMController {

    @Autowired
    TransactionRepository transactions;

    @Autowired
    TransactionService transactionService;

   @RequestMapping("/")
   public String showMainPage()
    {
        return "index";
    }

    @RequestMapping("/deposit")
    public String makeDeposit(Model model)
    {
        model.addAttribute("currentTransaction",new Transaction());
        return "deposit";
    }

    @PostMapping("/deposit")
    public String saveDeposit(@ModelAttribute("currentTransaction") Transaction toSave, Model model)
    {
        toSave.deposit(toSave.getAmount());
        toSave.setAction("Deposit");
        transactions.save(toSave);
        model.addAttribute("msg","You deposited "+toSave.getAmount()+" into your account");
        return "index";
    }

    @RequestMapping("/withdraw")
    public String makeWithdrawal(Model model)
    {
        model.addAttribute("currentTransaction",new Transaction());
        return "withdraw";
    }

    @PostMapping("/withdraw")
    public String saveWithdrawal(@ModelAttribute("currentTransaction") Transaction toSave, Model model)
    {
        toSave.setAction("Withdrawal");
        toSave.withdraw(toSave.getAmount());
        model.addAttribute("msg","You withdrew "+Math.abs(toSave.getAmount())+" from your account");
        transactions.save(toSave);
        return "index";
    }

    @GetMapping("/viewbalance")
    public String viewBalance(ModelMap model)
    {

        model.put("currentTransaction",new Transaction());
        model.remove("ask");
        return "showbalance";
    }

    @PostMapping("/viewbalance")
    public String showBalance(Model model, HttpServletRequest request)
    {
        String theAccountNumber = request.getParameter("theAccount");
        if(transactions.countAllByAccountNumber(theAccountNumber)<1)
        {
            model.addAttribute("msg","Unable to locate the account entered");
            model.addAttribute("ask",true);
        }
        else{
            model.addAttribute("transactions",transactionService.getTransactionSummary(theAccountNumber));
            model.addAttribute("balance",transactions.sumAmountByAccountNumber(theAccountNumber));
            model.addAttribute("ask",false);
        }

        return "showbalance";
    }



}
