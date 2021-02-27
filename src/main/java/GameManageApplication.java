import domain.bettingMoney.BettingMoney;
import domain.lotto.LottoTicket;
import domain.lotto.LottoTickets;
import domain.lotto.TicketCount;
import domain.lotto.WinningLotto;
import domain.result.Result;
import service.LottoService;
import view.InputView;
import view.LottoGameScreen;
import view.dto.LottoGameResultDto;

import java.util.Set;

public class GameManageApplication {
    private final LottoGameScreen lottoGameScreen;
    private final LottoService lottoService;
    private final InputView inputView;

    public GameManageApplication(final LottoGameScreen lottoGameScreen, final LottoService lottoService, final InputView inputView) {
        this.lottoGameScreen = lottoGameScreen;
        this.lottoService = lottoService;
        this.inputView = inputView;
    }

    public void run() {
        BettingMoney bettingMoney = getBettingMoney();
        TicketCount ticketCount = bettingMoney.getTicketCount(LottoTicket.TICKET_PRICE);
        lottoGameScreen.showTicketCount(ticketCount);
        LottoTickets lottoTickets = lottoService.getLottoTickets(inputView, ticketCount, inputView.inputManualTicketCount());
        lottoGameScreen.showAllLottoStatus(lottoTickets.getLottoTickets());

        viewGameResult(bettingMoney, lottoTickets);
    }

    private void viewGameResult(BettingMoney bettingMoney, LottoTickets lottoTickets) {
        WinningLotto winningLotto = getWinningLotto();
        Result result = new Result(lottoTickets, winningLotto);
        lottoGameScreen.showGameResult(new LottoGameResultDto(result.getResults()));
        lottoGameScreen.showRevenueResult(result.findEarningsRate(bettingMoney));
    }

    private BettingMoney getBettingMoney() {
        int input = inputView.inputBettingMoney();
        return new BettingMoney(input);
    }

    private WinningLotto getWinningLotto() {
        Set<Integer> winningNumbers = inputView.inputWinningNumbers();
        int bonusNumber = inputView.inputBonusNumber();
        return new WinningLotto(winningNumbers, bonusNumber);
    }
}
