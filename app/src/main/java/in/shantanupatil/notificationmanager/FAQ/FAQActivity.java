package in.shantanupatil.notificationmanager.FAQ;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.shantanupatil.notificationmanager.R;

public class FAQActivity extends AppCompatActivity implements FAQInterface{

    RecyclerView faq;
    ArrayList<FAQList> faqLists;
    FAQAdapter faqAdapter;

    ClickController clickController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);


        clickController = new ClickController(this);

        faq = (RecyclerView) findViewById(R.id.faq) ;
        faq.setHasFixedSize(true);
        faq.setLayoutManager(new LinearLayoutManager(this));
        faqLists = new ArrayList<>();
        String[] questions = {  "How to use this Application?",
                                "How can I contribute to this project?",
                                "How can I Add TNP Articles?",
                                "How can I Add Notice?",
                                "How this app works?",
                                "Is my email address safe?",
                                "Are you storing our passwords?",
                            };
        String[] answers = {"This application is really easy to use. Only Internet connection is required. \n" +
                            "There are three different sections\n\n\t1. Events\n\t2. Notice\n\t3. TNP Articles",
                            "Thankyou so much for your interest. If you're willing to contribute your efforts to the project" +
                            "then you only need to check the github repository for that. " +
                                    "\n\nFollow https://github.com/shantanupatil/notification",
                            "Adding TNP Articles is easy and can be only done if you're authenticated for that. We will provide TNP Coordinator the ability" +
                                    "to post/publish an article by using the application itself. If you're logged in as a TNP Coordinator follow the steps: \n\n" +
                                    "\n\t1. Click Add TNP" +
                                    "\n\n\t2. Add Image of Article (It can be of Company Image or Anything)" +
                                    "\n\n\t3. Add title and Description" +
                                    "\n\n\t4. Click on Publish Article",
                            "Adding Notice is easy and can be only done if you're authenticated for that. We will provide Staff Members the ability" +
                            "to post/publish a notice by using the application itself. If you're logged in as a staff member follow the steps: \n\n" +
                            "\t1. Click Add Notice" +
                                    "\n\n\t2. Add Image of Notice" +
                                    "\n\n\t3. Add your name (Important to distinguish Notices)" +
                                    "\n\n\t4. Add title and Description" +
                                    "\n\n\t5. Select the Branch you prefer to add Notice" +
                                    "\n\n\t6. Click on Add Notice",
                            "Until and unless you're a developer you don't need to know the working of it. Still if you want to see the code" +
                            "follow another FAQ \"How can I contribute to this project?\"",
                            "Yes, we're using firebase for Authentication purpose so you're safe.",
                            "For some reasons we are not storing anyone\'s password. We only store user\'s Name, Branch, and some" +
                            "type of token (registration token) just to distinguish them. It may happen that we store Users password in future.",
                            };

        for (int i = 0; i < questions.length; i++ ) {
            FAQList faqList = new FAQList(questions[i], answers[i]);
            faqLists.add(faqList);
        }
        faqAdapter = new FAQAdapter();
        faq.setAdapter(faqAdapter);
    }

    @Override
    public void startDetailActivity(String title, String description) {
        Intent intent = new Intent(getApplicationContext(), FAQDetails.class);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        startActivity(intent);
    }

    public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.faq_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            FAQList faqList = faqLists.get(position);
            Log.d("TestFAQ", faqList.getTitle());
            holder.title.setText(faqList.getTitle());
        }

        @Override
        public int getItemCount() {
            return faqLists.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView title;
            RelativeLayout relative_faq;

            public ViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.faq_text);
                relative_faq = (RelativeLayout) itemView.findViewById(R.id.relative_faq);
                relative_faq.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                FAQList listItem = faqLists.get(this.getAdapterPosition());
                clickController.onItemClick(listItem);
            }
        }
    }
}
