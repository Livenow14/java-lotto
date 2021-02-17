import domain.LottoGameMachine;
import domain.budget.Budget;
import util.InputUtil;
import view.MainScreen;

public class GameManageApplication {

    public void run() {
        LottoGameMachine lottoGameMachine = lottoGameManageInitialize();
        lottoGameMachine.gameStart();
    }

    private LottoGameMachine lottoGameManageInitialize() {
        MainScreen mainScreen = new MainScreen();
        mainScreen.showInputMoney();
        int input = InputUtil.nextInt();
        Budget budget = Budget.amounts(input);
        LottoGameMachine lottoGameMachine = new LottoGameMachine(budget);
        return lottoGameMachine;
    }
}
