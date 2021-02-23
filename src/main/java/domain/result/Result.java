package domain.result;

import domain.bettingMoney.BettingMoney;
import domain.lotto.LottoTickets;
import domain.lotto.WinningLotto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Result {
    private final LottoTickets lottoTickets;
    private final WinningLotto winningLotto;
    private final Map<LottoRank, Integer> results;

    public Result(LottoTickets lottoTickets, WinningLotto winningLotto) {
        this.lottoTickets = lottoTickets;
        this.winningLotto = winningLotto;
        this.results = new HashMap<>();
        setResult();
    }

    public BigDecimal findEarningsRate(BettingMoney bettingMoney) {
        int prize = results.keySet().stream()
                .mapToInt(lottoRank -> lottoRank.getPrize() * results.get(lottoRank))
                .sum();
        return bettingMoney.getEarningRate(prize);
    }

    public Map<LottoRank, Integer> getResults() {
        return results;
    }

    private Map<LottoRank, Integer> setResult() {
        List<LottoRank> lottoRanks = lottoTickets.findMatches(winningLotto);
        lottoRanks.forEach(this::putResult);
        return results;
    }

    private void putResult(final LottoRank lottoRank) {
        results.computeIfPresent(lottoRank, (key, value) -> value + 1);
        results.putIfAbsent(lottoRank, 1);
    }
}
