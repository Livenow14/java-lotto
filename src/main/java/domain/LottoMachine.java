package domain;

import domain.ball.LottoBall;
import domain.ball.LottoBalls;
import domain.lotto.LottoTicket;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static domain.ball.LottoBalls.LOTTO_BALL_SIZE;

public class LottoMachine {
    public List<LottoTicket> makeRandomTickets(final int ticketCount) {
        return IntStream.range(0, ticketCount)
                .mapToObj(count -> makeRandomTicket())
                .collect(Collectors.toList());
    }

    private LottoTicket makeRandomTicket() {
        List<LottoBall> lottoBalls = getRandomLottoBalls();
        return new LottoTicket(new LottoBalls(lottoBalls));
    }

    public List<LottoTicket> makeManualTickets(final List<Integer> manualTicketNumbers, final int ticketCount) {
        if (ticketCount == 0) {
            return Collections.emptyList();
        }
        return IntStream.range(0, ticketCount)
                .mapToObj(count -> makeManualTicket(manualTicketNumbers))
                .collect(Collectors.toList());
    }

    private LottoTicket makeManualTicket(final List<Integer> manualTicketNumbers) {
        List<LottoBall> lottoBalls = manualTicketNumbers.stream()
                .map(LottoBall::from)
                .collect(Collectors.toList());
        return new LottoTicket(new LottoBalls(lottoBalls));
    }

    private List<LottoBall> getRandomLottoBalls() {
        List<LottoBall> lottoBalls = LottoBall.getLottoBalls();
        Collections.shuffle(lottoBalls);
        return lottoBalls.stream()
                .limit(LOTTO_BALL_SIZE)
                .sorted()
                .collect(Collectors.toList());
    }
}
