package uk.co.darkerwaters.scorepal.activities.fragments;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import uk.co.darkerwaters.scorepal.R;
import uk.co.darkerwaters.scorepal.activities.BaseActivity;
import uk.co.darkerwaters.scorepal.activities.handlers.MatchRecyclerAdapter;
import uk.co.darkerwaters.scorepal.players.Team;
import uk.co.darkerwaters.scorepal.score.base.Point;
import uk.co.darkerwaters.scorepal.score.pingpong.PingPongMatch;
import uk.co.darkerwaters.scorepal.score.pingpong.PingPongScore;

public class LayoutPingPongSummary extends LayoutScoreSummary<PingPongMatch> {

    private final static long K_ANIMATION_DURATION = 750;

    TextView teamOneTitle;
    TextView teamTwoTitle;

    TextView teamOnePoints;
    TextView teamTwoPoints;

    TextView teamOneRounds;
    TextView teamTwoRounds;

    private ImageView servingImageView;

    public LayoutPingPongSummary() {
        super();
    }


    @Override
    public View createView(LayoutInflater inflater, ViewGroup container) {
        // create the layout on the parent view
        View mainView = inflater.inflate(R.layout.layout_ping_pong_summary, container, false);
        initialiseViewContents(mainView);
        return mainView;
    }

    @Override
    public void initialiseViewContents(View mainView) {
        // this main view is our parent, use this
        super.initialiseViewContents(mainView);

        this.teamOneTitle = mainView.findViewById(R.id.textViewTeamOne);
        this.teamTwoTitle = mainView.findViewById(R.id.textViewTeamTwo);
        this.teamOnePoints = mainView.findViewById(R.id.teamOne_Points);
        this.teamTwoPoints = mainView.findViewById(R.id.teamTwo_Points);
        this.teamOneRounds = mainView.findViewById(R.id.teamOne_Rounds);
        this.teamTwoRounds = mainView.findViewById(R.id.teamTwo_Rounds);

        // set the text colour for team one
        int color = parent.getContext().getColor(R.color.teamOneColor);
        this.teamOneTitle.setTextColor(color);
        this.teamOnePoints.setTextColor(color);
        this.teamOneRounds.setTextColor(color);

        color = parent.getContext().getColor(R.color.teamTwoColor);
        this.teamTwoTitle.setTextColor(color);
        this.teamTwoPoints.setTextColor(color);
        this.teamTwoRounds.setTextColor(color);

        this.servingImageView = mainView.findViewById(R.id.servingImageView);
        this.servingImageView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showCurrentServer(PingPongMatch match) {
        if (null != this.servingImageView) {
            if (null != match && !match.isMatchOver()) {
                Team teamServing = match.getTeamServing();
                if (teamServing.equals(match.getTeamOne())) {
                    // animate back to where we started
                    this.servingImageView.animate()
                            .translationX(0f)
                            .withStartAction(new Runnable() {
                                @Override
                                public void run() {
                                    servingImageView.setVisibility(View.VISIBLE);
                                    BaseActivity.setupButtonIcon(servingImageView, R.drawable.ic_baseline_arrow_forward, R.color.teamOneColor);
                                }
                            })
                            .setDuration(K_ANIMATION_DURATION)
                            .start();
                    BaseActivity.setupButtonIcon(servingImageView, R.drawable.ic_baseline_arrow_forward, R.color.teamOneColor);
                } else {
                    // work out how far down from the top set text changer to the bottom we need to go
                    float distance = (teamTwoPoints.getX() - teamOnePoints.getX()) + teamOnePoints.getWidth() + servingImageView.getWidth() + 4;
                    // and animate to here
                    this.servingImageView.animate()
                            .translationX(distance)
                            .withStartAction(new Runnable() {
                                @Override
                                public void run() {
                                    servingImageView.setVisibility(View.VISIBLE);
                                    BaseActivity.setupButtonIcon(servingImageView, R.drawable.ic_baseline_arrow_back, R.color.teamTwoColor);
                                }
                            })
                            .setDuration(K_ANIMATION_DURATION)
                            .start();
                    BaseActivity.setupButtonIcon(servingImageView, R.drawable.ic_baseline_arrow_back, R.color.teamTwoColor);
                }
            }
            else {
                this.servingImageView.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void setMatchData(final PingPongMatch match, final MatchRecyclerAdapter.MatchFileListener source) {
        super.setMatchData(match, source);
        // set all the data from this match on this view
        Context context = parent.getContext();
        PingPongScore score = match.getScore();
        Team teamOne = match.getTeamOne();
        Team teamTwo = match.getTeamTwo();

        // who won
        Team matchWinner = match.getMatchWinner();

        // set the titles
        this.teamOneTitle.setText(teamOne.getTeamName());
        this.teamTwoTitle.setText(teamTwo.getTeamName());

        if (matchWinner.equals(teamOne)) {
            // make team one's name bold
            BaseActivity.setTextViewBold(this.teamOneTitle);
        }
        else if (matchWinner.equals(teamTwo)) {
            // make team two's name bold
            BaseActivity.setTextViewBold(this.teamTwoTitle);
        }

        // scroll these names
        this.teamOneTitle.setSelected(true);
        this.teamTwoTitle.setSelected(true);

        // set the points
        Point teamOnePoint = score.getDisplayPoint(teamOne);
        this.teamOnePoints.setText(teamOnePoint.displayString(context));
        Point teamTwoPoint = score.getDisplayPoint(teamTwo);
        this.teamTwoPoints.setText(teamTwoPoint.displayString(context));

        if (teamOnePoint.val() > teamTwoPoint.val()) {
            setTextViewBold(this.teamOnePoints);
            setTextViewNoBold(this.teamTwoPoints);
        }
        else if (teamTwoPoint.val() > teamOnePoint.val()) {
            setTextViewBold(this.teamTwoPoints);
            setTextViewNoBold(this.teamOnePoints);
        }
        else {
            setTextViewNoBold(this.teamOnePoints);
            setTextViewNoBold(this.teamTwoPoints);
        }

        // set the rounds
        Point teamOneRound = score.getDisplayRound(teamOne);
        this.teamOneRounds.setText(teamOneRound.displayString(context));
        Point teamTwoRound = score.getDisplayRound(teamTwo);
        this.teamTwoRounds.setText(teamTwoRound.displayString(context));

        if (teamOneRound.val() > teamTwoRound.val()) {
            setTextViewBold(this.teamOneRounds);
            setTextViewNoBold(this.teamTwoRounds);
        }
        else if (teamTwoRound.val() > teamOneRound.val()) {
            setTextViewBold(this.teamTwoRounds);
            setTextViewNoBold(this.teamOneRounds);
        }
        else {
            setTextViewNoBold(this.teamOneRounds);
            setTextViewNoBold(this.teamTwoRounds);
        }
    }
}