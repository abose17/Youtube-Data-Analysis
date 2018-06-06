Degree Distribution:
It contains three java files. 
Indegree.java calculates indegree for the dataset.
Outdegree.java calculates outdegree fo the dataset.
Totaldegree.java calculates total degree based on output of indegree and outdegree.


Categorization Statistics
1. 1_mean_cmnt_cnt_by_ctgr.java: calculates the average number of comment by each video category.
2. 2_mean_ratng_cnt_by_ctgr.java: calculates the average number of ratings by each video category.
3. 3_mean_view_cnt_by_ctgr.java: calculates the average number of view count by each video category.
4. 4_No_of_video_by_ctgr.java: calculates the number of videos by each video category.
5. 5_size_ctgr_rng_id_cnt.java: calculates the id count belongs to particular size range by providing a category name and two integer values for ranges as user input.
6. 6_size_with_id_count.java: calculates the id count belongs to particular size range by providing two integer values for ranges as user input.
7. 7_view_cnt_ctgr.java: calculates the frequency of view count of videos by sequential ranges of view-count.
8. 8_size_cnt_ctgr.java: calculates the frequency of videos by sequential ranges of size.
9. 9_top_k_rtd_video_Id.java: calculates top X-rated video ID. Firstly, it calculates the ratings of each video. Then, using Linux command (sort –n –k7 –r | head –nX), we can get the IDs of top X-rated videos. 
10. 10_top_k_pop_video_id.java: calculates top Y-rated video ID. Firstly, it calculates the ratings of each video. Then, using Linux command (sort –n –k5 –r | head –nY), we can get the IDs of top Y-rated videos.


Analysis
This folder contains different commands in R for analysis.
Each section is divided by hash sign (##) and has comments.

